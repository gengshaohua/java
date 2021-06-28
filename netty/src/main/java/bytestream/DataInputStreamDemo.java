package bytestream;

import java.io.*;

public class DataInputStreamDemo {

    public static void main(String[] args) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("C:\\project\\java\\hello.txt"));
        // 写入byte类型数据
        dataOutputStream.writeByte(20);
        // 写入short类型数据
        dataOutputStream.writeShort(30);
        // 写入int类型
        dataOutputStream.writeInt(900);
        // 写入float类型
        dataOutputStream.writeFloat(12.3f);
        // 写入long类型
        dataOutputStream.writeLong(800L);
        // 写入double类型
        dataOutputStream.writeDouble(14.23);
        //写入boolean类型
        dataOutputStream.writeBoolean(true);
        // 写入char类型
        dataOutputStream.writeChar('中');
        dataOutputStream.close();
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream("C:\\project\\java\\hello.txt"));
        System.out.println(dataInputStream.readByte());
        System.out.println(dataInputStream.readShort());
        System.out.println(dataInputStream.readInt());
        System.out.println(dataInputStream.readFloat());
        System.out.println(dataInputStream.readLong());
        System.out.println(dataInputStream.readDouble());
        System.out.println(dataInputStream.readBoolean());
        System.out.println(dataInputStream.readChar());

        dataInputStream.close();
       /* //创建一个对象
        PrintStream printStream = new PrintStream("C:\\project\\java\\hello.txt");
        //写入一个字节数组
        printStream.write("helloworld".getBytes());
        //写入一个换行符号
        printStream.println();
        //格式化写入数据
        printStream.format("文件名称:%s","hello.txt");
        printStream.println();
        printStream.append("abcde" );
        printStream.close();*/
    }

}
