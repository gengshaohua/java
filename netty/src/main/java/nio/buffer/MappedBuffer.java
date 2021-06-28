package nio.buffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedBuffer {

    public static final int start = 0;
    public static final int size = 1024;

    public static void main(String[] args) throws Exception {
        RandomAccessFile raf = new RandomAccessFile("C:\\project\\java\\netty\\src\\main\\resources\\test.txt", "rw");
        FileChannel channel = raf.getChannel();
        //把缓冲区跟文件系统进行一个映射关系
        //只要操作缓冲区里面的内容，文件也会跟着改变
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, size, size);
        mappedByteBuffer.put(0, (byte)99);
        mappedByteBuffer.put(4, (byte)122);
        raf.close();
    }

}
