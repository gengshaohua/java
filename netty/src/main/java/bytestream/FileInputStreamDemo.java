package bytestream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileInputStreamDemo {

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File("C:\\project\\java\\netty\\src\\main\\resources\\test.txt"));
        try {
            //1.直接通过read将全部读取到byte数组
            byte[] bytes = new byte[1024];
            fileInputStream.read(bytes);
            System.out.println(new String(bytes));
            fileInputStream.close();
            //2.通过read一个字节一个字节读取
            fileInputStream = new FileInputStream(new File("C:\\project\\java\\netty\\src\\main\\resources\\test.txt"));
            int by;
            while((by = fileInputStream.read()) != -1){
                System.out.print((char)by);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
