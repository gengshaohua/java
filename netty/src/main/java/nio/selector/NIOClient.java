package nio.selector;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class NIOClient {

    public static final String SERVER_HOST = "localhost";
    private final static int SERVER_PORT = 8080;
    private static Selector selector;
    private static SocketChannel socketChannel;


    public NIOClient(){
        try {
            //开启选择器
            selector = Selector.open();
            //开启连接服务端通道
            socketChannel = SocketChannel.open(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
       new NIOClient();
       new Thread(() -> {
           while(true){
               try {
                   int read = selector.select();
                   if (read > 0) {
                       Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                       while (iterator.hasNext()) {
                           SelectionKey key = iterator.next();
                           if (key.isReadable()) {
                               SocketChannel socketChannel = (SocketChannel) key.channel();
                               ByteBuffer buffer = ByteBuffer.allocate(1024);
                               int len = socketChannel.read(buffer);
                               if (len > 0) {
                                   String content = new String(buffer.array());
                                   System.out.println(content);
                               }
                           }
                           iterator.remove();
                       }
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }).start();
       Scanner scanner = new Scanner(System.in);
       while (scanner.hasNextLine()){
           String message = scanner.nextLine();
           socketChannel.write(ByteBuffer.wrap(message.getBytes()));
       }
    }

}
