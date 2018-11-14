package com.ldg.study.springCloud.socket.io.stream;

import java.io.*;

/**
 * 以字节方式读取或输出
 *
 * @author： ldg
 * @create date： 2018/11/13
 */
public class ByteStream {
    // 数据媒介：
    // 文件、管道、网络连接、内存缓存、System.in out error
    public static void main(String[] args) throws IOException {

        // 字节输入流操作
        InputStream input = new ByteArrayInputStream("abcd".getBytes());
        int data = input.read();
        while (data != -1) {
            System.out.println((char) data);
            data = input.read();
        }

        // 字节输出流
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write("12345".getBytes());
        byte[] ob = output.toByteArray();
    }
}
