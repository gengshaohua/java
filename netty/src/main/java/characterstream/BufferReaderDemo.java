package characterstream;

import java.io.*;

public class BufferReaderDemo {
    public static void main(String[] args) throws IOException {
        //从控制台得到输入流
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        //创建文件
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\project\\java\\netty\\src\\main\\resources\\hello.txt"));
        String input = null;
        while(!"exit".equals(input = bufferedReader.readLine())){
            //将从控制台得到的数据写入文件
            bufferedWriter.write(input);
            //写入一个当前系统下的空行
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        bufferedReader.close();

    }
}
