package bytestream;

import java.io.*;

public class ObjectInputStreamDemo {

    public static void main(String[] args) throws Exception {
        byte[] bytes = new byte[]{97,99,121};
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("worm.out"));
        //序列化对象，把对象写到worm.out里面
        out.writeObject("Worm storage");
        //序列化对象，把对象写到worm.out里面
        out.close();
        //从worm.out里面读取对象
        ObjectInputStream in=new ObjectInputStream(new FileInputStream("worm.out"));
        String toString = in.readObject().toString();
        System.out.println(toString);
    }

}
