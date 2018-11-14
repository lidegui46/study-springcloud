package com.ldg.study.springCloud.socket.io.stream;

import java.io.*;

/**
 * 字符流
 *
 * @author： ldg
 * @create date： 2018/11/13
 */
public class CharStream {
    // 数据媒介：
    // 文件、管道、网络连接、内存缓存、System.in out error
    public static void main(String[] args) throws IOException {

        // 字符输入流操作
        Reader reader = new CharArrayReader("abcd".toCharArray());
        int data  = reader.read();
        while(data != -1){
            System.out.println((char)data);
            data = reader.read();
        }

        // 字符输出流
        CharArrayWriter writer = new CharArrayWriter();
        writer.write("12345".toCharArray());
        char[] wc = writer.toCharArray();
    }
}
