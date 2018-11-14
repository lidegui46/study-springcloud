package com.ldg.study.springCloud.socket.nio.talk;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelThread implements Runnable {
    private SocketChannel socketChannel;
    private String remoteName;

    public SocketChannelThread(SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        this.remoteName = socketChannel.getRemoteAddress().toString();
        System.out.println("client " + remoteName + " 连接成功");
    }

    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            try {
                buffer.clear();
                int read = socketChannel.read(buffer);
                if (read != -1) {
                    buffer.flip();
                    StringBuilder sb = new StringBuilder();
                    while (buffer.hasRemaining()) {
                        sb.append((char) buffer.get());
                    }
                }
            } catch (IOException e) {
                try {
                    socketChannel.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 查看Buffer的作用
     * <pre>
     *     缓冲区不是线程安全
     *     Buffer详解：https://www.cnblogs.com/niejunlei/p/5994130.html
     * </pre>
     *
     * @param args
     */
    public static void main(String[] args) {
        // 正常 Buffer
        print("normol", getNewBuffer());

        // clear后的Buffer，清理后恢复到 Buffer 初始化 ，等同于“正常 Buffer”
        print("clear", getNewBuffer().clear());

        // “可添加数据元素的Buffer” 转换为 “一个准备读出元素”的释放状态
        // flip() = limit(position()).position(0)
        print("flip", getNewBuffer().flip());

        //
        // rewind() = position(0)
        print("rewind", getNewBuffer().rewind());

        //
        print("mark", getNewBuffer().mark());

        //
        //print("reset", getNewBuffer().reset());
    }

    private static Buffer getNewBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(30);
        buffer.put("hello".getBytes());
        return buffer;
    }

    private static void print(String type, Buffer buffer) {
        StringBuilder sb = new StringBuilder("-- " + type + " --").append("\r\n")
                // 缓存大小（可存最大数量）
                .append("        ").append("capacity :").append(buffer.capacity()).append("\r\n")
                // 下一个被读取元素的位置，put 或 add 后动态更新
                .append("        ").append("position :").append(buffer.position()).append("\r\n")
                // 最大存储元素数量
                .append("        ").append("limit :").append(buffer.limit()).append("\r\n")
                // 剩余可存元素数量
                .append("        ").append("remaining :").append(buffer.remaining()).append("\r\n")
                // 是否有可存元素
                .append("        ").append("hasRemaining :").append(buffer.hasRemaining()).append("\r\n");
        System.out.println(sb.toString());
    }
}
