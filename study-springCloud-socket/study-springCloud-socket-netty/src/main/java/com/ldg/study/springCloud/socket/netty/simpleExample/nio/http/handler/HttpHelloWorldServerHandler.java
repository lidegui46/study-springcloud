package com.ldg.study.springCloud.socket.netty.nio.http.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.AsciiString;
import util.UrlUtil;

import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpHelloWorldServerHandler extends ChannelInboundHandlerAdapter {
    //private static final byte[] CONTENT = {'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd'};
    private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
    private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
    private static final AsciiString CONNECTION = new AsciiString("Connection");
    private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // TODO 页面上有多个连接地址时，会多次请求
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;


            // 可通过Url地址响应不同的处理器
            Map<String, List<String>> urlParam = UrlUtil.getUrlParam(req.getUri());

            StringBuilder sbUrl = new StringBuilder();
            for (Map.Entry<String, List<String>> entry : urlParam.entrySet()) {
                sbUrl.append(entry.getKey()).append(" : ");
                for (String str : entry.getValue()) {
                    sbUrl.append(str).append(" , ");
                }
                sbUrl.append(" ; ");
            }

            String serverReply = "服务端回复：Hello Word，客户端地址：" + sbUrl.toString();

            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(serverReply.getBytes()));
            response.headers().set(CONTENT_TYPE, "text/plain; charset=utf-8");
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

            if (!HttpUtil.isKeepAlive(req)) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, KEEP_ALIVE);
                ctx.write(response);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
