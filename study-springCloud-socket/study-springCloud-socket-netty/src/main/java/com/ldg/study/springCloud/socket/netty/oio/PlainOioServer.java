package com.ldg.study.springCloud.socket.netty.simpleExample.oio;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PlainOioServer {
    public void serve(int port) throws IOException {
        // 开启Socket服务器，并监听端口
        final ServerSocket socket = new ServerSocket(port);
        try{
            for(;;){
                // 轮训接收监听
                final Socket clientSocket = socket.accept();
                try {
                    Thread.sleep(500000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("accepted connection from "+clientSocket);
                // 创建新线程处理请求
                new Thread(()->{
                   OutputStream out;
                   try{
                       out = clientSocket.getOutputStream();
                       out.write("Hi\r\n".getBytes(CharsetUtil.UTF_8));
                       out.flush();
                   } catch (IOException e) {
                       e.printStackTrace();
                   } finally {
                       try{
                           clientSocket.close();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
                }).start();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        PlainOioServer server = new PlainOioServer();
        server.serve(5555);
    }
}
