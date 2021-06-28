package bio.tomcat.servlet;

import bio.tomcat.http.GPRequest;
import bio.tomcat.http.GPResponse;

import java.io.IOException;

public class SecondServlet extends AbstractServlet{
    @Override
    public void doGet(GPRequest request, GPResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(GPRequest request, GPResponse response) throws IOException {
        response.write("This is Second Servlet");
    }
}
