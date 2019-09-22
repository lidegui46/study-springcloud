package com.ldg.study.springCloud.socket.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Author:  ldg
 * Date:    2019/9/21 13:48
 * Desc:    this is file description
 */
public class SelectorClient {

    public static void main(String[] args) {
        try {
            testClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testClient() throws IOException {
        // 连接地址、端口号
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9090);
        // 1、获取通道（channel）
        SocketChannel socketChannel = SocketChannel.open(address);
        // 2、切换成非阻塞模式
        socketChannel.configureBlocking(false);
        // 3、分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 数据 写入 缓冲区
        byteBuffer.put("hello world  ".getBytes());
        // Buffer 切换到 读模式
        byteBuffer.flip();
        // 向 通道 写入 Buffer 数据
        socketChannel.write(byteBuffer);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        byteBuffer.clear();
        socketChannel.read(byteBuffer);
        byteBuffer.flip();
        System.out.println(byteBuffer.array());

        socketChannel.close();
    }
}
