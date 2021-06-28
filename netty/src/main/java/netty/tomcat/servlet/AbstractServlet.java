package netty.tomcat.servlet;


import netty.tomcat.http.GPRequest;
import netty.tomcat.http.GPResponse;

import java.io.IOException;

public abstract class AbstractServlet {

    public abstract void doGet(GPRequest request, GPResponse response) throws IOException;
    public abstract void doPost(GPRequest request, GPResponse response) throws IOException;

    public void service(GPRequest request, GPResponse response) throws IOException {
        if("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request, response);
        }else{
            doPost(request, response);
        }
    }

}
