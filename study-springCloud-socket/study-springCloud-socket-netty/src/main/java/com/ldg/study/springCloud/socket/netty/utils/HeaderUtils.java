package com.ldg.study.springCloud.socket.netty.simpleExample.utils;

import com.ldg.study.springCloud.socket.netty.simpleExample.nio.talk.MessageHeaderDto;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @author： ldg
 * @create date： 2018/11/16
 */
public class HeaderUtils {
    private final static int applicationIdLength = 33;
    private final static int sendUserIdLength = 33;
    private final static int bodyLength = 4;

    public static ByteBuf transformToByteBuf(MessageHeaderDto headerDto) {
        byte[] applicationIdBytes = headerDto.getApplicationId().getBytes();
        byte[] sendUserIdBytes = headerDto.getSendUserId().getBytes();

        //TODO 验证长度

        ByteBuf buffer = Unpooled.buffer(0);
        buffer.writeBytes(applicationIdBytes)
                .writeBytes(sendUserIdBytes)
                .writeInt(headerDto.getBodyLength());

        return buffer;
    }

    public static MessageHeaderDto transformToHeader(ByteBuf headerBytes) {
        ByteBuf applicationIdBuffer = Unpooled.buffer(applicationIdLength);
        ByteBuf sendUserBuffer = Unpooled.buffer(sendUserIdLength);
        ByteBuf bodyLengthBuffer = Unpooled.buffer(bodyLength);

        headerBytes.readBytes(applicationIdBuffer);
        headerBytes.readBytes(sendUserBuffer);
        headerBytes.readBytes(bodyLengthBuffer);

        System.out.println(applicationIdBuffer.toString(Charset.forName("UTF-8")));
        System.out.println(sendUserBuffer.toString(Charset.forName("UTF-8")));
        System.out.println(bodyLengthBuffer.getInt(3));

        //return new MessageHeaderDto();
        return null;
    }

    public static void main(String[] args) {
        MessageHeaderDto messageHeaderDto =
                new MessageHeaderDto("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1"
                        , "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb1", 10);

        ByteBuf byteBuf = transformToByteBuf(messageHeaderDto);
        MessageHeaderDto messageHeaderDto1 = transformToHeader(byteBuf);

    }
}
