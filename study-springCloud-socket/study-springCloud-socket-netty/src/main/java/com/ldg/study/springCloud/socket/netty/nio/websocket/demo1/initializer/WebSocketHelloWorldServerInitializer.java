package com.ldg.study.springCloud.socket.netty.simpleExample.nio.websocket.demo1.initializer;

import com.ldg.study.springCloud.socket.netty.simpleExample.nio.websocket.demo1.handler.WebSocketHelloWorldServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author donald
 * 2017年6月30日
 * 上午11:42:49
 */
public class WebSocketHelloWorldServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public WebSocketHelloWorldServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
        // 支持http协议的解析
        // HttpServerCodec和HttpObjectAggregator已经帮我们封装好了WebSocket的握手FullHttpRequest/FullHttpResponse包和各类数据Frame包.
        p.addLast(new HttpServerCodec());

        p.addLast(new HttpObjectAggregator(65536));

        // 对于大文件支持 chunked方式写
        p.addLast(new ChunkedWriteHandler());

        // 对websocket协议的处理--握手处理, ping/pong心跳, 关闭
        // WebSocketServerProtocolHandler隐藏了握手的细节处理, 以及心跳处理和关闭响应. 多个ChannelHanlder的叠加和WebSocket协议本身的复杂是密切先关的
        // WebSocketServerProtocolHandler 开启后，客户端调用时，url地址后必须包含“/websocketTest”，如：http://17.0.0.1/websocketTest，否则访问不了
        //p.addLast(new WebSocketServerProtocolHandler("/websocketTest"));

        //p.addLast(new HttpServerExpectContinueHandler());
        // 自定义 处理器
        p.addLast(new WebSocketHelloWorldServerHandler());
    }
}
