package com.ldg.study.springCloud.socket.netty.simpleExample.nio.talk;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息头实体
 *
 * @author： ldg
 * @create date： 2018/11/16
 */
@Setter
@Getter
public class MessageHeaderDto {
    /**
     * 应用编号
     */
    private String applicationId;
    /**
     * 发送者编号
     */
    private String sendUserId;
    /**
     * 消息体长度
     */
    private int bodyLength;

    public MessageHeaderDto(String applicationId, String sendUserId, int bodyLength) {
        this.applicationId = applicationId;
        this.sendUserId = sendUserId;
        this.bodyLength = bodyLength;
    }
}
