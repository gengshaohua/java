package nio.buffer;

import java.nio.IntBuffer;

public class ReadOnlyBuffer {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        for(int i = 0; i<buffer.capacity(); i++){
            buffer.put(i);
        }

        //创建只读缓冲区
        IntBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        for(int i = 0; i<buffer.capacity(); i++){
            buffer.put(i, i*10);
        }
        readOnlyBuffer.position(0);
        readOnlyBuffer.limit(buffer.capacity());

        while(readOnlyBuffer.hasRemaining()){
            System.out.print(readOnlyBuffer.get() + "\t");
        }
    }

}
