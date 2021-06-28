package bio.tomcat.http;

import java.io.InputStream;

public class GPRequest {

    private String method;
    private String url;

    public GPRequest(InputStream inputStream) {
        try{
            String content = null;
            byte[] bytes = new byte[1024];
            int len;
            if((len = inputStream.read(bytes)) > 0){
                content = new String(bytes, 0, len);
            }
            assert content != null;
            System.out.println(content);
            String line = content.split("\\n")[0];
            String[] arr = line.split("\\s");
            this.method = arr[0];
            this.url = arr[1].split("\\?")[0];
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getMethod() {
        return this.method;
    }

    public String getUrl() {
        return this.url;
    }
}
