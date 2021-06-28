package bytestream;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.IOException;

public class ByteInputStreamDemo {

    public static void main(String[] args) throws IOException {
        byte[] bytes = new byte[]{97,99,121};
        ByteInputStream byteInputStream = new ByteInputStream(bytes, bytes.length);
        int by;
        while((by = byteInputStream.read()) != -1){
            System.out.print((char)by);
        }
        byteInputStream.close();
    }
}
