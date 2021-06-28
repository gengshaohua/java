package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

public class AIOClient {

    private final AsynchronousSocketChannel client;

    public AIOClient() throws IOException {
        client = AsynchronousSocketChannel.open();
    }

    public void connect(String host, int port){
        client.connect(new InetSocketAddress(host, port), null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void result, Void attachment) {
                try {
                    client.write(ByteBuffer.wrap("这是一条测试数据".getBytes())).get();
                    System.out.println("已发送至服务器");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                exc.printStackTrace();
            }
        });
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        client.read(buffer, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                System.out.println("IO操作读取完毕" + result);
                System.out.println("获取反馈结果" + new String(buffer.array()));
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
        try{
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new AIOClient().connect("localhost", 8080);
    }

}
