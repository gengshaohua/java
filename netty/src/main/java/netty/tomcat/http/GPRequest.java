package netty.tomcat.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

public class GPRequest {

    private ChannelHandlerContext ctx;

    private HttpRequest request;

    public GPRequest(ChannelHandlerContext ctx, HttpRequest request){
        this.ctx = ctx;
        this.request = request;
    }

    public String getUrl() { return request.uri();}
    public String getMethod() { return request.method().name();}

    public Map<String, List<String>> getParameters(){
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        return decoder.parameters();
    }

    public String getParameter(String name){
        Map<String, List<String>> parameters = getParameters();
        List<String> strings = parameters.get(name);
        if(null == strings){
            return null;
        }
        return strings.get(0);
    }
}
