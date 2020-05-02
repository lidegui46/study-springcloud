package com.ldg.study.springCloud.socket.netty.oio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class NettyOioServer {
    public void serve(int port) throws InterruptedException {
        final ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi\r\b", Charset.forName("UTF-8")));

        // 创建Boss线程组
        EventLoopGroup bossGroup = new OioEventLoopGroup(1);
        // 创建Worker线程组，默认线程数量与CPU的核的数量有关系
        EventLoopGroup workerGroup = new OioEventLoopGroup();

        try{
            // 创建BootstrapServer
            ServerBootstrap b = new ServerBootstrap();


            b.group(bossGroup, workerGroup)//配置boss和worker
                    .channel(OioServerSocketChannel.class) // 使用阻塞的SocketChannel
                    .localAddress(new InetSocketAddress(port)) // 绑定端口号
                    .handler(new SimpleServerHandler()) // 定义服务器启动的时候经过的状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception { // 定义worker接收到数据的时候都干嘛
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ctx.writeAndFlush(buf.duplicate())
                                            .addListener(ChannelFutureListener.CLOSE);
                                }
                            });
                        }
                    });
            // 绑定服务器并接收连接
            ChannelFuture f = b.bind().sync();
            // 等待服务器关闭socket
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NettyOioServer server = new NettyOioServer();
        server.serve(5555);
    }

    private static class SimpleServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channelActive");
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channelRegistered");
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            System.out.println("handlerAdded");
        }
    }
}
