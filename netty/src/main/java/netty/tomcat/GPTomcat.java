package netty.tomcat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import netty.tomcat.http.GPRequest;
import netty.tomcat.http.GPResponse;
import netty.tomcat.servlet.AbstractServlet;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GPTomcat {


    private final static int PORT = 8080;

    private ServerSocket serverSocket;
    private final Map<String, AbstractServlet> servletMapping = new HashMap<>();

    private final Properties webXml = new Properties();

    //初始化
    private void init(){
        try {
            String webInfoPath = getClass().getResource("/").getPath();
            FileInputStream inputStream = new FileInputStream(webInfoPath + "web.properties");

            webXml.load(inputStream);
            for(Object k : webXml.keySet()){
                String key = k.toString();
                if(key.endsWith(".url")){
                    String servletName = key.replace(".url", "");
                    String url = webXml.getProperty(key);
                    String className = webXml.getProperty(servletName + ".className");
                    AbstractServlet servlet = (AbstractServlet) Class.forName(className).newInstance();
                    servletMapping.put(url, servlet);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start(){

        init();

        //netty封装NIO，Rector模型 Boss， worker
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new HttpResponseEncoder());
                            channel.pipeline().addLast(new HttpRequestDecoder());
                            channel.pipeline().addLast(new GPTomcatHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = server.bind(PORT).sync();
            System.out.println("GP Tomcat已启动，监听的端口:" + PORT);
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public class GPTomcatHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if(msg instanceof HttpRequest){
                HttpRequest request = (HttpRequest) msg;
                GPRequest gpRequest = new GPRequest(ctx, request);
                GPResponse gpResponse = new GPResponse(ctx, request);
                String url = gpRequest.getUrl();
                if(servletMapping.containsKey(url)){
                    servletMapping.get(url).service(gpRequest, gpResponse);
                }else{
                    gpResponse.write("404 - NOT FOUND");
                }
            }
        }
    }


}
