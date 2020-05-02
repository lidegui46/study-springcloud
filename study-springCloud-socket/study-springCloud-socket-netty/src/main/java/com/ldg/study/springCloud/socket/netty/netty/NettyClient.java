package com.ldg.study.springCloud.socket.netty.simpleExample.netty;

import com.ldg.study.springCloud.socket.netty.simpleExample.nio.discardByBuffer.handler.DiscardServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Author:  ldg
 * Date:    2019/9/20 18:25
 * Desc:    this is file description
 */
public class NettyClient {
    public static void main(String[] args) {
        client();
    }

    private static void client() {
        // 1 启动引导器
        Bootstrap bootstrap = new Bootstrap();
        // 3 用于处理 接收或发送数据 的线程组
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            // 4 设置reactor 线程
            bootstrap.group(workerLoopGroup);
            // 5 设置 服务端 的网络通道（NioServerSocketChannel：非阻塞服务端TCP通道）（通道：NIO、OIO；协议：Socket、Datagram、stcp）
            bootstrap.channel(NioSocketChannel.class);
            // 8 设置 接收或发送数据（workerLoopGroup） 通道选项
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            // 10 装配 接收或发送数据 流水线（workerLoopGroup）
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
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
            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9090);
            //连接socket地址
            // 11 开始绑定server （阻塞：sync（同步方法）阻塞 直到 绑定成功）
            ChannelFuture channelFuture = bootstrap.connect(inetSocketAddress).sync();
            System.out.println(NettyClient.class.getName() + " started and listen on " + channelFuture.channel().localAddress());

            // 12 监听通道关闭事件（阻塞：应用程序会一直等待，直到 channel 关闭）
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 13 优雅关闭EventLoopGroup （释放掉所有资源包括创建的线程）
            workerLoopGroup.shutdownGracefully();
        }
    }
}
