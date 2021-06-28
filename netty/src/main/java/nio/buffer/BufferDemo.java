package nio.buffer;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo {

    public static void main(String[] args) throws Exception {
        URL url = BufferDemo.class.getClassLoader().getResource("test.txt");
        assert url != null;
        //文件IO处理
        FileInputStream in = new FileInputStream(new File(url.getFile()));
        //创建文件的操作管道
        FileChannel fc = in.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(20);
        output("init()", buffer);
        //读取到buffer
        fc.read(buffer);
        output("read()", buffer);
        //锁定操作范围，修改limit为position position为0
        buffer.flip();
        output("flip()", buffer);
        //判断有没有可读数据
        while(buffer.remaining() > 0){
            byte b = buffer.get();
            System.out.print((char)b);
        }
        output("get()", buffer);
        //解锁
        buffer.clear();
        output("clear()", buffer);
        in.close();
    }

    public static void output(String step, Buffer buffer){
        System.out.print(step + " : ");
        //容量，数组大小
        System.out.print("capacity: " + buffer.capacity() + ",");
        //当前操作数据所在的位置
        System.out.print("position: " + buffer.position() + ",");
        //锁定值，flip,position-limit
        System.out.print("limit: " + buffer.limit() + "\n");
    }

}
