package com.ldg.study.springCloud.socket.io.talk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TalkServer {
    public static void main(String[] args) {
        try {
            // 创建ServerSocket
            ServerSocket server = new ServerSocket(5555);
            // 阻塞并等待连接
            Socket socket = server.accept();

            // 获得client的输入流
            BufferedReader receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 获得server的输出流
            PrintWriter print = new PrintWriter(socket.getOutputStream());

            // 读取 client 内容
            String line = receive.readLine();
            while (!"bye".equals(line)) {
                // server 回应 client
                print.println("ok");
                // 发送
                print.flush();

                // 输出 client 内容
                System.out.println("client:" + line);

                // 继续读取 client 内容
                line = receive.readLine();
            }
            print.close();
            receive.close();
            socket.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
