package bio.tomcat.http;

import java.io.IOException;
import java.io.OutputStream;

public class GPResponse {

    private final OutputStream out;

    public GPResponse(OutputStream outputStream) {
        out = outputStream;
    }


    public void write(String content) throws IOException {
        String sb = "HTTP/1.1 200 OK\n" +
                "Content-Type: text/html;\n" +
                "\r\n" +
                content;
        out.write(sb.getBytes());
    }
}
