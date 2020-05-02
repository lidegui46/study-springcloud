package com.ldg.study.springCloud.socket.netty.nio.discardByBuffer.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Handles a server-side channel.
 *
 * @author donald
 * 2017年6月16日
 * 上午9:36:53
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(DiscardServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("【顺序：3】" + ctx.channel().remoteAddress() + " ====> Online ");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("【顺序：9】" + ctx.channel().remoteAddress() + " ====> Shutdown ");
    }

    /**
     * 读client通道数据，通道处理器上下文ChannelHandlerContext与Mina的会话很像
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("【顺序：5】" + ctx.channel().remoteAddress() );
        receiveMessage((ByteBuf) msg);
        ackMessage(ctx);
        // 触发顺序 “7”
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("【顺序：7】" + ctx.channel().remoteAddress() + " ====> ReadComplete ");
    }

    /**
     * 接收消息
     *
     * @param msg
     */
    private void receiveMessage(ByteBuf msg) {
        ByteBuf in = null;
        try {
            in = msg;
    		/*
    		ByteBuffer buffer = ByteBuffer.allocate(1024);
    		in.getBytes(0, buffer);
    		buffer.flip();
    		*/
            log.info("===Server reciever message:" + in.toString(CharsetUtil.UTF_8));
        } finally {
            // 如果msg为引用计数对象，在使用后注意释放，一般在通道handler中释放
            // ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 回复消息
     *
     * @param ctx
     */
    private void ackMessage(ChannelHandlerContext ctx) {
        // Initialize the message.
        ByteBuf out = ctx.alloc().directBuffer(1024);
        String ackMessage = "hello client ...";
        try {
            // 内容写入缓冲区
            out.writeBytes(ackMessage.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        out.retainedDuplicate();

        // 写队列 并 刷新写队列
        // 触发顺序 "6"
        ctx.writeAndFlush(out);
    	/* 这两句与上面一句效果等同
            // 写队列
            ctx.write(out);
            // 刷新写队列
            ctx.flush();
    	*/

        //对于Write(ByteBuf)方法，一般不用自动释放ByteBuf，
        //ctx会帮我们释放ByteBuf
        //out.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info("【顺序：8】" + ctx.channel().remoteAddress() );
        //异常发生时，关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}