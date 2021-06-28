package bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {

    ServerSocket serverSocket;

    //优化线程池
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public BIOServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("服务器已启动");
    }

    public void listener() {
        while(true){
            try {
                System.out.println("等待客户端连接.....（阻塞中）");
                Socket socket = serverSocket.accept();
                System.out.println("客户端连接");
                //handler(socket);
                //优化使用线程池分配处理
                cachedThreadPool.execute(() -> {
                    handler(socket);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handler(Socket socket) {
        //对方法数据给我了，读 Input
        InputStream is;
        try {
            is = socket.getInputStream();
            //网络客户端把数据发送到网卡，机器所得到的数据读到了JVM内中
            byte [] buff = new byte[1024];
            /*int len = is.read(buff);
            if(len > 0){
                String msg = new String(buff,0,len);
                System.out.println("收到" + msg);
            }*/
            while (true){
                System.out.println("等待客户端输入.....（阻塞中）");
                int read = is.read(buff);
                if (read != -1){
                    System.out.println(new String(buff, 0, read));
                }else {
                    break;
                }
            }
            is.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new BIOServer(8080).listener();
    }

}
