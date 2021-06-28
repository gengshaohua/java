package netty.rpc.registry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class RpcRegistry {

    private int port;

    public RpcRegistry(int port){
        this.port = port;
    }

    public void start(){
        //创建boss、worker线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //自定义协议编解器
                            /**
                             * 入参为5个，分别解释下
                             * maxFrameLength：框架最大长度，如果帧的长度大于此值，则将抛出TooLongFrameException
                             * lengthFiledOffset：长度字段的偏移量，及对应的长度字段在整个消息的位置
                             * lengthFiledLength：长度字段的长度，长度字段是int型，那么这个值就是4 long就是8
                             * lengthAdjustment：要添加到长度字段值的补偿值
                             * initialBytesToStrip：从解码帧中去除的第一个字节数
                             */
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            //自定义协议编码器
                            pipeline.addLast(new LengthFieldPrepender(4));
                            //对象参数类型解码器
                            pipeline.addLast("encoder", new ObjectEncoder());
                            //对象参数类型解码器
                            pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(new RegistryHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture sync = server.bind(port).sync();
            System.out.println("GP RPC Registry start listener at" + port);
            sync.channel().closeFuture().sync();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new RpcRegistry(8080).start();;
    }

}
