package com.ldg.study.springCloud.socket.netty.nio.http.initializer;

import com.ldg.study.springCloud.socket.netty.nio.http.handler.HttpHelloWorldServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

import java.nio.charset.Charset;

/**
 * 
 * @author donald
 * 2017年6月30日
 * 上午11:42:49
 */
public class HttpHelloWorldServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public HttpHelloWorldServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
        p.addLast(new HttpServerCodec());
        // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
        p.addLast(new HttpResponseDecoder());
        p.addLast(new HttpObjectAggregator(65536));
        // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
        p.addLast(new HttpRequestEncoder());

        p.addLast(new StringEncoder(Charset.forName("UTF-8")));
        p.addLast(new StringDecoder(Charset.forName("UTF-8")));

        //p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new HttpHelloWorldServerHandler());
    }
}
