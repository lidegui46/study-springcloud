
package com.ldg.study.springCloud.socket.netty.simpleExample.nio.http;

import com.ldg.study.springCloud.socket.netty.simpleExample.nio.http.initializer.HttpHelloWorldServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Http访问服务端
 * <pre>
 *     客户端向服务端发起请求
 *          客户端：可 同步 接收服务端返回的内容
 *          服务端：接收客户端请求的内容，注：目前只发现通过Url进行访问
 *     访问地址：
 *          http://127.0.0.1:8080/a.html?age=34&name=%E6%9D%8E%E5%9B%9B&english=lidegui
 * </pre>
 */
public final class HttpHelloWorldServer {
    private static final Logger log = LoggerFactory.getLogger(HttpHelloWorldServer.class);
    static final boolean SSL = System.getProperty("ssl") != null;
    private static final String ip = "127.0.0.1";
    static final int port = Integer.parseInt(System.getProperty("port", SSL ? "8443" : "8080"));

    public static void main(String[] args) throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpHelloWorldServerInitializer(sslCtx));

            InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);

            Channel ch = b.bind(inetSocketAddress).sync().channel();
            log.info("=========HttpServer is start=========");
            System.err.println("Open your web browser and navigate to " +
                    (SSL ? "https" : "http") + "://" + ip + ":" + port + '/');
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
