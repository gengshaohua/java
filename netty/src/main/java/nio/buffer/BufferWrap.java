package nio.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class BufferWrap {

    public static void main(String[] args) {
        //分配指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(10);

        //包装现有的数组
        byte[] array = new byte[10];
        ByteBuffer buffer1 = ByteBuffer.wrap(array);
    }

}
