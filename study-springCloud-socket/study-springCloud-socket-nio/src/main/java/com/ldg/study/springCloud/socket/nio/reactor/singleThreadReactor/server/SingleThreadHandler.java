package com.ldg.study.springCloud.socket.nio.reactor.singleThreadReactor.server;

import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.Selector;
import java.util.HashMap;
import java.util.Map;

/**
 * 事件 抽象类
 * Author:  ldg
 * Date:    2019/9/21 23:46
 * Desc:    this is file description
 */
public abstract class SingleThreadHandler {
    public static final String HANDLER_ACCEPT = "acceptHandler";
    public static final String HANDLER_READ = "readHandler";
    public static final String HANDLER_WRITE = "writeHandler";

    Selector selector;

    static Map<String, SingleThreadHandler> handlerMap = new HashMap<>(2);

    static {
        if (CollectionUtils.isEmpty(handlerMap)) {
            synchronized (SingleThreadHandler.class) {
                if (CollectionUtils.isEmpty(handlerMap)) {
                    handlerMap.put(HANDLER_ACCEPT, new SingleThreadAcceptorHandler());
                    handlerMap.put(HANDLER_READ, new SingleThreadReadHandler());
                    handlerMap.put(HANDLER_WRITE, new SingleThreadWriteHandler());
                }
            }
        }
    }

    public static void findAndRegister(String handlerName, Selector selector, Channel channel) {
        if (handlerMap.containsKey(handlerName)) {
            SingleThreadHandler singleThreadHandler = handlerMap.get(handlerName);
            try {
                singleThreadHandler.register(selector, channel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    abstract void register(Selector selector, Channel channel) throws IOException;

    abstract void run();
}
