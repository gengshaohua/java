package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AIOServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        new AIOServer();
    }

    public AIOServer(){
        listener();
    }

    private void listener() {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            AsynchronousChannelGroup threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
            final AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(threadGroup);
            server.bind(new InetSocketAddress(PORT));
            System.out.println("服务已启动，监听端口" + PORT);

            server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    System.out.println("IO操作完成，开始获取数据");
                    try{
                        byteBuffer.clear();
                        result.read(byteBuffer).get();
                        byteBuffer.flip();
                        result.write(byteBuffer);
                        byteBuffer.flip();
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }finally {
                        try {
                            result.close();
                            server.accept(null, this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("操作完成");
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("IO操作异常" + exc);
                }
            });
            try{
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
