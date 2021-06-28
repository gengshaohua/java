package netty.rpc.consumer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import netty.rpc.protocol.InvokerProtocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcProxy {

    public static <T> T create(Class<?> clazz){
        MethodProxy proxy = new MethodProxy(clazz);
        Class<?>[] interfaces = clazz.isInterface() ?
                new Class[]{clazz} :
                clazz.getInterfaces();
        T result = (T) Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, proxy);
        return result;
    }


    public static class MethodProxy implements InvocationHandler{

        private Class<?> clazz;

        public MethodProxy(Class<?> clazz){
            this.clazz = clazz;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //判断传来的是一个接口还是一个实现类
            if(Object.class.equals(method.getDeclaringClass())){
                try {
                    method.invoke(this, args);
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }else{
                return rpcInvoke(proxy, method, args);
            }
            return null;
        }

        private Object rpcInvoke(Object proxy, Method method, Object[] args) {
            InvokerProtocol msg = new InvokerProtocol();
            msg.setClassName(this.clazz.getName());
            msg.setMethodName(method.getName());
            msg.setValues(args);
            msg.setParams(method.getParameterTypes());

            final RpcProxyHandler consumerHandler = new RpcProxyHandler();
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                ChannelPipeline pipeline = socketChannel.pipeline();
                                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                                //自定义协议编码器
                                pipeline.addLast(new LengthFieldPrepender(4));
                                //对象参数类型解码器
                                pipeline.addLast("encoder", new ObjectEncoder());
                                //对象参数类型解码器
                                pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE,
                                        ClassResolvers.cacheDisabled(null)));
                                pipeline.addLast(consumerHandler);
                            }
                        });
                ChannelFuture sync = bootstrap.connect("localhost", 8080).sync();
                sync.channel().writeAndFlush(msg).sync();
                sync.channel().closeFuture().sync();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                group.shutdownGracefully();
            }
            return consumerHandler.getResponse();
        }
    }

}
