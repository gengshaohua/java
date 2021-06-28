package nio.buffer;

import java.nio.IntBuffer;

public class IntBufferDemo {

    public static void main(String[] args) {
        //分配新的int缓冲区，参数为缓冲区容量
        //新的缓冲区position为0，limit为其容量，底层实现数组，数组偏移量设置为0
        IntBuffer buffer = IntBuffer.allocate(8);

        for(int i = 0; i < buffer.capacity(); i++){
            int j = 2 * (i+1);
            //放入缓冲区当前位置，进行递增position
            buffer.put(j);
        }
        //重设此缓冲区，limit设置为当前position，然后将当前position设为0
        buffer.flip();
        //查询当前位置和限制位置之间是否有元素
        while(buffer.hasRemaining()){
            //读取次缓冲区当前位置的整数，进行递增position
            int j = buffer.get();
            System.out.print(j + "\t");
        }
    }

}
