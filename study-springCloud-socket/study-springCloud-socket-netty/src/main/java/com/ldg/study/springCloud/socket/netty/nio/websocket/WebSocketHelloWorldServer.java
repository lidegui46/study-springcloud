
package com.ldg.study.springCloud.socket.netty.nio.websocket;

import com.ldg.study.springCloud.socket.netty.nio.websocket.initializer.WebSocketHelloWorldServerInitializer;
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

/**
 * An HTTP server that sends back the content of the received HTTP request
 * in a pretty plaintext form.
 *
 * @author donald
 * 2017年6月30日
 * 上午11:44:43
 */
public final class WebSocketHelloWorldServer {
    private static final Logger log = LoggerFactory.getLogger(WebSocketHelloWorldServer.class);
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
                    .childHandler(new WebSocketHelloWorldServerInitializer(sslCtx));
            //InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
            Channel ch = b.bind(port).sync().channel();

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
