package nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    private final static int PORT = 8080;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public NIOServer(){
        try {
            //开启选择器
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            //创建可选择通道，配置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //绑定通道到指定端口
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            //向Selector中注册感兴趣的事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listener(){
        System.out.println("listen on" + PORT);
        try {
            while(true){
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iter = keys.iterator();
                while(iter.hasNext()){
                    SelectionKey next = iter.next();
                    iter.remove();
                    process(next);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(SelectionKey next) throws IOException {
        //接受请求
        if(next.isAcceptable()){
            SocketChannel channel = serverSocketChannel.accept();
            channel.configureBlocking(false);
            InetSocketAddress remoteAddress = (InetSocketAddress) channel.getRemoteAddress();
            System.out.println(remoteAddress.getAddress() + "\t" +
                    remoteAddress.getHostName() + "\t" +
                            remoteAddress.getPort() + "\t" +
                    "连接成功");
            channel.register(selector, SelectionKey.OP_READ);
        }
        //读信息
        else if(next.isReadable()){
            SocketChannel channel = (SocketChannel) next.channel();
            ByteBuffer buff = ByteBuffer.allocate(1024);
            int len = channel.read(buff);
            if(len > 0){
                buff.flip();
                String content = new String(buff.array(), 0, len);
                SelectionKey key = channel.register(selector, SelectionKey.OP_WRITE);
                key.attach(content);
                System.out.println("获取结果" + content);
            }else{
                channel.close();
            }
            buff.clear();
        }
        //写事件
        else if(next.isWritable()){
            SocketChannel channel = (SocketChannel) next.channel();
            System.out.println("触发写事件");
            String content = "回复" + next.attachment();
            ByteBuffer block = ByteBuffer.wrap(content.getBytes());
            channel.write(block);
            channel.close();
        }
    }



    public static void main(String[] args) throws IOException {
        new NIOServer().listener();
    }
}