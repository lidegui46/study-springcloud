package com.ldg.study.springCloud.socket.nio.reactor.singleThreadReactor.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 服务端发送数据的 Handler
 * Author:  ldg
 * Date:    2019/9/21 14:26
 * Desc:    this is file description
 */
public class SingleThreadWriteHandler extends SingleThreadHandler {
    SocketChannel socketChannel;
    SelectionKey selectionKey;
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    int count = 0;

    @Override
    public void register(Selector selector, Channel channel) throws IOException {
        this.socketChannel = (SocketChannel) channel;
        this.socketChannel.configureBlocking(false);
        this.selector = selector;

        // 注册 写 就绪
        selectionKey = this.socketChannel.register(selector, SelectionKey.OP_WRITE);
        //将Handler作为callback对象
        selectionKey.attach(this);
    }

    public void run() {
        try {
            if (this.socketChannel.isConnected() && this.socketChannel.isOpen()) {
                replayContent();
                this.socketChannel.write(byteBuffer);
                //write完就结束了, 关闭select key
                if (isComplete()) {
                    selectionKey.cancel();
                }
                // 连接成功，向 Selector 注册 读 就绪状态
                SingleThreadHandler.findAndRegister(SingleThreadHandler.HANDLER_READ, selector, socketChannel);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void replayContent() {
        count++;
        String replay = new StringBuilder("hello,my name is server ").append(count).toString();
        byteBuffer.clear();
        byteBuffer.put(replay.getBytes());
        byteBuffer.flip();
    }

    boolean isComplete() {
        /* ... */
        return false;
    }

    void process() {
        /* ... */
        return;
    }
}