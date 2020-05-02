package com.ldg.study.springCloud.socket.netty.simpleExample.nio.talk.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;

public class ClientMain {
    public static void main(String[] args) throws InterruptedException {

        final ByteBuf buffer = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Are you OK!\r\n", Charset.forName("UTF-8")));

        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try{
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    System.out.println("channel read");
                                    ByteBuf result = (ByteBuf)msg;
                                    System.out.println("server said:"+result.toString(Charset.forName("utf-8")));
                                }

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("channel active");
                                    ctx.writeAndFlush(buffer);
                                }
                            });
                        }
                    });
            ChannelFuture f = bootstrap.connect("localhost",5555).sync();
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }
}
