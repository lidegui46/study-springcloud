package com.ldg.study.springCloud.socket.nio.reactor.singleThreadReactor.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.channels.*;

/**
 * 网络 连接 请求 handler
 * 注：可根据不类协议的 Channel 调用不同协议的执行器
 * Author:  ldg
 * Date:    2019/9/21 23:45
 * Desc:    this is file description
 */
public class SingleThreadAcceptorHandler extends SingleThreadHandler {
    private ServerSocketChannel serverSocketChannel;

    @Override
    public void register(Selector selector, Channel channel) throws IOException {
        this.serverSocketChannel = (ServerSocketChannel) channel;
        this.selector = selector;

        //分步处理,第一步,接收accept事件
        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //attach callback object, AcceptorHandler1
        sk.attach(this);
    }

    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                // 连接成功，向 Selector 注册 接收 就绪状态
                SingleThreadHandler.findAndRegister(SingleThreadHandler.HANDLER_READ, selector, socketChannel);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}