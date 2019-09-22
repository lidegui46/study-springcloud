package com.ldg.study.springCloud.socket.nio.reactor.multiThreadReactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * Author:  ldg
 * Date:    2019/9/21 14:35
 * Desc:    this is file description
 */
public class MultiThreadReactor implements Runnable {

    final int selectorSize = 2;
    //subReactors集合, 一个selector代表一个subReactor
    Selector[] selectors = new Selector[selectorSize];
    int next = 0;
    final ServerSocketChannel serverSocket;

    MultiThreadReactor(int port) throws IOException { //Reactor初始化
        //selectors[0] = Selector.open();
        //selectors[1] = Selector.open();
        openSelector();

        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        //非阻塞
        serverSocket.configureBlocking(false);

        //分步处理,第一步,接收accept事件
        SelectionKey sk = serverSocket.register(selectors[0], SelectionKey.OP_ACCEPT);
        //attach callback object, Acceptor
        sk.attach(new Acceptor());
    }

    void openSelector() {
        Arrays.stream(selectors).forEach(selector -> {
            try {
                selector = Selector.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                for (int i = 0; i < selectorSize; i++) {
                    selectors[i].select();
                    Set selected = selectors[i].selectedKeys();
                    Iterator it = selected.iterator();
                    while (it.hasNext()) {
                        //Reactor负责dispatch收到的事件
                        dispatch((SelectionKey) (it.next()));
                    }
                    selected.clear();
                }

            }
        } catch (IOException ex) { /* ... */ }
    }

    void dispatch(SelectionKey k) {
        Runnable r = (Runnable) (k.attachment());
        //调用之前注册的callback对象
        if (r != null) {
            r.run();
        }
    }

    class Acceptor { // ...
        public synchronized void run() throws IOException {
            SocketChannel connection = serverSocket.accept(); //主selector负责accept
            if (connection != null) {
                new MultiThreadHandler(selectors[next], connection); //选个subReactor去负责接收到的connection
            }
            if (++next == selectors.length) next = 0;
        }
    }
}