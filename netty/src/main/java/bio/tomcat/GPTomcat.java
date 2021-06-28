package bio.tomcat;

import bio.tomcat.http.GPRequest;
import bio.tomcat.http.GPResponse;
import bio.tomcat.servlet.AbstractServlet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GPTomcat {

    private final static int PORT = 8080;

    private ServerSocket serverSocket;
    private final Map<String, AbstractServlet> servletMapping = new HashMap<>();

    private final Properties webXml = new Properties();

    //配置好端号，默认8080 ServerSocket IP:localhost
    //配置web.xml 自己写的Servlet继承HttpServlet
    //读取配置 与Servlet建立映射关系

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

    //启动
    public void start() {
        init();
        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("Tomcat已启动，监听端口：" + PORT);
            //等待用户请求，死循环
            while(true){
                Socket accept = serverSocket.accept();
                process(accept);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        
        
    }

    private void process(Socket accept) throws IOException {
        InputStream inputStream = accept.getInputStream();
        OutputStream outputStream = accept.getOutputStream();
        GPRequest request = new GPRequest(inputStream);
        GPResponse response = new GPResponse(outputStream);

        String url = request.getUrl();

        if(servletMapping.containsKey(url)){
            servletMapping.get(url).service(request, response);
        }else{
            response.write("404 - NOT FOUND");
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
        accept.close();
    }

    public static void main(String[] args) {
        new GPTomcat().start();
    }
}
