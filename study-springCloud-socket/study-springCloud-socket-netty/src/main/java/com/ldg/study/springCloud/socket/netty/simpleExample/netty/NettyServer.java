package com.ldg.study.springCloud.socket.netty.netty;

import com.ldg.study.springCloud.socket.netty.nio.discardByBuffer.handler.DiscardServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * Author:  ldg
 * Date:    2019/9/20 18:25
 * Desc:    this is file description
 */
public class NettyServer {
    public static void main(String[] args) {
        server(9090);
    }
private static void server(int port) {
    // 1 启动引导器
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    // 2 用于处理 网络连接 的线程组
    EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
    // 3 用于处理 接收或发送数据 的线程组
    EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
    try {
        // 4 设置reactor 线程
        serverBootstrap.group(bossLoopGroup, workerLoopGroup);
        // 5 设置 服务端 的网络通道（NioServerSocketChannel：非阻塞服务端TCP通道）（通道：NIO、OIO；协议：Socket、Datagram、stcp）
        serverBootstrap.channel(NioServerSocketChannel.class);
        // 6 设置监听端口
        serverBootstrap.localAddress(new InetSocketAddress(port));
        // 7 设置 网络连接（boosLoopGroup） 通道选项
        serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        // 8 设置 接收或发送数据（workerLoopGroup） 通道选项
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        // 9 装配 网络连接 流水线（bossLoopGroup）
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        serverBootstrap.handler(new ChannelInitializer<NioServerSocketChannel >()  {
            @Override
            protected void initChannel(NioServerSocketChannel  channel) throws Exception {
                // 此处的作用是 bossLoopGroup 启动后执行？
                System.out.println("服务端启动中");
            }
        });
        // 10 装配 接收或发送数据 流水线（workerLoopGroup）
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            // 有连接到达时会创建一个channel
            protected void initChannel(SocketChannel ch) throws Exception {
                // pipeline 管理 channel 中的 Handler
                // 入站：从头到尾 依次执行“继承了 ChannelInboundHandler”的实现类
                // 出站：从尾到头 依次执行“继承了 ChannelOutboundHandler”的实现类
                //ch.pipeline().addLast(new ProtobufDecoder());
                //ch.pipeline().addLast(new ProtobufEncoder());
                ch.pipeline().addLast("serverHandler", new DiscardServerHandler());
            }
        });
        // 11 开始绑定server （阻塞：sync（同步方法）阻塞 直到 绑定成功）
        ChannelFuture channelFuture = serverBootstrap.bind().sync();
        System.out.println(NettyServer.class.getName() + " started and listen on " + channelFuture.channel().localAddress());

        // 12 监听通道关闭事件（阻塞：应用程序会一直等待，直到 channel 关闭）
        ChannelFuture closeFuture = channelFuture.channel().closeFuture();
        closeFuture.sync();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        // 13 优雅关闭EventLoopGroup （释放掉所有资源包括创建的线程）
        workerLoopGroup.shutdownGracefully();
        bossLoopGroup.shutdownGracefully();
    }
}
}
