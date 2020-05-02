package com.ldg.study.springCloud.socket.netty.simpleExample.nio.websocket.demo1.config;

import io.netty.channel.Channel;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author： ldg
 * @create date： 2018/11/21
 */
public class ChannelContext {
    //public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static ConcurrentMap<SocketAddress, Channel> channelMap = new ConcurrentHashMap<SocketAddress, Channel>();

    public static void put(Channel channel) {
        if (!channelMap.containsKey(channel.remoteAddress())) {
            channelMap.put(channel.remoteAddress(), channel);
        }
    }

    public static Channel get(Channel channel) {
        return channelMap.get(channel.remoteAddress());
    }

    public static void remove(Channel channel) {
        if (channelMap.containsKey(channel.remoteAddress())) {
            channelMap.remove(channel.remoteAddress(), channel);
        }
    }
}
