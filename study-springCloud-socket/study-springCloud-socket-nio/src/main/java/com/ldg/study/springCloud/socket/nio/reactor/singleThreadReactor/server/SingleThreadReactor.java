package com.ldg.study.springCloud.socket.nio.reactor.singleThreadReactor.server;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 反应器
 * 作用：服务端注册 及多路复用吕
 * Author:  ldg
 * Date:    2019/9/21 14:25
 * Desc:    this is file description
 */
public class SingleThreadReactor implements DisposableBean {
    private final Selector selector;
    private final ServerSocketChannel serverSocket;

    public SingleThreadReactor(int port) throws IOException {
        //Reactor初始化
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        //非阻塞
        serverSocket.configureBlocking(false);

        // 注册 接收 就绪状态
        SingleThreadHandler.findAndRegister(SingleThreadHandler.HANDLER_ACCEPT, selector, serverSocket);
    }

    /**
     * 监听 Selector“就绪状态”
     */
    public void monitorReadiness() {
        try {
            while (!Thread.interrupted()) {
                Assert.isTrue(serverSocket.isOpen(), "server socket channel is closed");
                selector.select();// 无就绪状态时，会阻塞
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                // **********   此处为单线程处理 socket accept，吞吐量较低  *************
                // **********   此处可考虑 线程池 来处理，合理使用服务器资源，提高吞吐量  *************
                selectedKeys.forEach(selectionKey -> {
                    if (selectionKey.attachment() instanceof SingleThreadHandler) {
                        SingleThreadHandler handler = (SingleThreadHandler) (selectionKey.attachment());
                        if (handler != null) {
                            handler.run();
                        }
                    }
                });
                selectedKeys.clear();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        this.close();
    }

    public void close() {
        this.closeChannel();
        this.closeSelector();
    }

    private void closeChannel() {
        if (serverSocket != null && serverSocket.isOpen()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        closeSelector();
    }

    private void closeSelector() {
        if (selector != null && selector.isOpen()) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
