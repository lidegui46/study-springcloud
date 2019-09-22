package com.ldg.study.springCloud.socket.nio.reactor.singleThreadReactor.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Author:  ldg
 * Date:    2019/9/21 13:48
 * Desc:    this is file description
 */
public class SingleThreadClient {

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

        // 写数据
        write(socketChannel, "hello,my name is client 1");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 读数据
        read(socketChannel);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 写数据
        write(socketChannel, "hello,my name is client 2");

        while (true) {
        }
        // socketChannel.close();
    }

    private static void write(SocketChannel socketChannel, String content) throws IOException {
// 3、分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 数据 写入 缓冲区
        byteBuffer.put(content.getBytes());
        // Buffer 切换到 读模式
        byteBuffer.flip();
        // 向 通道 写入 Buffer 数据
        socketChannel.write(byteBuffer);
    }

    private static void read(SocketChannel socketChannel) throws IOException {
        ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(readByteBuffer);
        readByteBuffer.clear();
        System.out.println("服务端 回复：" + new String(readByteBuffer.array()));
    }
}
