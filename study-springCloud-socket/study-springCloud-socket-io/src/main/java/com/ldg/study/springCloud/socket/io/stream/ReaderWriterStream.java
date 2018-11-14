package com.ldg.study.springCloud.socket.io.stream;

import java.io.*;

/**
 * 读取流
 * <pre>
 *     inputstream  : 一次返回一个字节 0-255
 *     reader       : 一次返回一个字符，0-65535，根据文本的编码决定读取几个字节
 * </pre>
 *
 * @author： ldg
 * @create date： 2018/11/13
 */
public class ReaderWriterStream {
    public static void main(String[] args) throws IOException {
        readStream();
        writerStream();
    }

    private static void readStream() throws IOException {
        String file = "a.txt";

        Reader reader = new FileReader(file);
        int data = reader.read();
        while (data != -1) {
            char dataChar = (char) data;
            System.out.println(dataChar);
            data = reader.read();
        }
    }

    private static void writerStream() throws IOException {
        String path = "a.txt";
        Writer writer = new FileWriter(path);
        writer.write("hello-1");
        writer.close();
    }
}
