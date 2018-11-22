package com.ldg.study.springCloud.socket.netty.nio.socketByClass.client;

import com.ldg.study.springCloud.socket.netty.nio.socketByClass.dto.SubscribeReq;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SubReqClientHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0; i<10 ; i++){
            ctx.writeAndFlush(subReq(i));
        }
    }

    private SubscribeReq subReq(int i){
        SubscribeReq req = new SubscribeReq();
        req.setAddress("大连");
        req.setPhoneNumber("151xxxx12345");
        req.setProductName("netty test");
        req.setSubReqID(i);
        req.setUserName("xingoo");
        return req;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response : ["+msg+"]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
