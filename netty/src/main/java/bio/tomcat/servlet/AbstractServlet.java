package bio.tomcat.servlet;

import bio.tomcat.http.GPRequest;
import bio.tomcat.http.GPResponse;

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
