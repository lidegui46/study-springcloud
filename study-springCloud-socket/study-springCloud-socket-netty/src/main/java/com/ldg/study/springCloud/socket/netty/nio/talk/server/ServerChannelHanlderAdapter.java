package com.ldg.study.springCloud.socket.netty.simpleExample.nio.talk.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;

/**
 * @author： ldg
 * @create date： 2018/11/16
 */
public class ServerChannelHanlderAdapter extends ChannelInboundHandlerAdapter {
    private void print(StringBuilder sb) {
        sb.insert(0, "\r\n--------------------------");
        System.out.println(sb.toString());
    }

    private StringBuilder getAddress(ChannelHandlerContext ctx) {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        return new StringBuilder()
                .append("\r\n")
                .append("      ")
                .append("Client : ")
                .append(socketAddress);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        print(getAddress(ctx).append(" Online..."));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        print(getAddress(ctx).append(" Shutdown..."));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        print(getAddress(ctx).append(" ").append(" read..."));
        // get header ；格式：长度
        ByteBuf headerBuyteBuf = Unpooled.buffer();

        //ctx.writeAndFlush(buffer);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        //ctx.close();
    }
}
