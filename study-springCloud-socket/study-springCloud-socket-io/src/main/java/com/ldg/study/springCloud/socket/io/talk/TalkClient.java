package com.ldg.study.springCloud.socket.io.talk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TalkClient {
    public static void main(String[] args) {
        try {
            // 创建连接
            Socket socket = new Socket("127.0.0.1", 5555);

            // 获得系统输入流
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));

            // 获得 client 的输出流，即：client -> server 发送内容请求
            PrintWriter print = new PrintWriter(socket.getOutputStream());

            // 获得 client 的输入流，即：server -> client 回执的内容
            BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 读取 client 输入的内容
            String content = sin.readLine();
            while (!content.equals("bye")) {
                // client -> server 打印内容
                print.println(content);
                // client -> server 发送
                print.flush();

                // 输出client 和 server回执内容
                System.out.println("client:" + content);
                System.out.println("server:" + read.readLine());

                // 继续 读取client输入的内容
                content = sin.readLine();
            }
            print.close();
            read.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
