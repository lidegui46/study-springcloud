package com.ldg.study.springCloud.socket.nio.reactor.singleThreadReactor.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 接收 客户端发送的数据 Handler
 * Author:  ldg
 * Date:    2019/9/21 14:26
 * Desc:    this is file description
 */
public class SingleThreadReadHandler extends SingleThreadHandler {
    SocketChannel socketChannel;

    @Override
    void register(Selector selector, Channel channel) throws IOException {
        this.socketChannel = (SocketChannel) channel;
        this.socketChannel.configureBlocking(false);
        this.selector = selector;

        //注册 读 就绪
        SelectionKey selectionKey = this.socketChannel.register(selector, SelectionKey.OP_READ);
        //将Handler作为callback对象
        selectionKey.attach(this);
    }

    public void run() {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            if (this.socketChannel.isConnected() && this.socketChannel.isOpen()) {
                int read = this.socketChannel.read(byteBuffer);
                if (read > 0) {
                    System.out.println("客户端：" + socketChannel.getRemoteAddress() + "，内容：" + new String(byteBuffer.array()));
                    if (isComplete()) {
                        process();

                        // 连接成功，向 Selector 注册 写 就绪状态
                        SingleThreadHandler.findAndRegister(SingleThreadHandler.HANDLER_WRITE, selector, socketChannel);
                    }
                } else {
                    this.socketChannel.close();
                }
                byteBuffer.clear();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    boolean isComplete() {
        /* ... */
        return true;
    }

    void process() {
        /* ... */
        return;
    }
}