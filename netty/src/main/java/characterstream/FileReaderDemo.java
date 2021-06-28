package characterstream;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileReaderDemo {
    public static void main(String[] args) throws IOException {
        FileWriter fileWriter = new FileWriter("C:\\project\\java\\netty\\src\\main\\resources\\hello.txt");
        //向文件里面写入文件
        fileWriter.write("123");
        //向文件里面写入文件，和writer的区别就是append返回的是FileWriter对象，而write没有返回值
        fileWriter.append("hello world");
        fileWriter.append("中");
        //把流中的数据刷新到文件中，还能继续使用
        // 如果没有刷新，也没有关闭流的话 数据是不会写入文件的
        fileWriter.flush();
        //关闭流
        fileWriter.close();
        FileReader fileReader = new FileReader("C:\\project\\java\\netty\\src\\main\\resources\\hello.txt");
        int len = 0;
        while ((len = fileReader.read()) != -1) {
            System.out.print((char) len);
        }
        //用char数组读数据。
        char[] chars = new char[1024];
        while ((len = fileReader.read(chars)) != -1) {
            System.out.print(chars);
        }
        fileReader.close();
    }
}
