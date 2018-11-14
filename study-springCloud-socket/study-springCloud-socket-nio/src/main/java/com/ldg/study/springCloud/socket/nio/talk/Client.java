package com.ldg.study.springCloud.socket.nio.talk;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(12345));
        while (true) {
            Scanner sc = new Scanner(System.in);

            byte[] bytes = sc.next().getBytes("UTF-8");
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);

            buffer.flip();

            while (buffer.hasRemaining()) {
                socketChannel.write(buffer);
            }
        }
    }
}
