package netty.tomcat.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.StringUtil;

import java.nio.charset.StandardCharsets;

public class GPResponse {

    //SocketChannel封装
    private ChannelHandlerContext ctx;

    private HttpRequest request;

    public GPResponse(ChannelHandlerContext ctx, HttpRequest request){
        this.ctx = ctx;
        this.request = request;
    }

    public void write(String content) throws Exception{
        try{
            if(StringUtil.isNullOrEmpty(content)){
                return;
            }
            //设置http协议及请求头信息
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(content.getBytes(StandardCharsets.UTF_8)));
            response.headers().set("Content-Type", "text/html");
            ctx.write(response);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ctx.close();
        }
    }

}
