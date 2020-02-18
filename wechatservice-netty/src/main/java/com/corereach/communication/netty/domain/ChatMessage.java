package com.corereach.communication.netty.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/18 19:37
 * @Version V1.0
 **/
@Data
@ToString
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = -4997196456624460145L;

    /**
     * 聊天内容
     */
    private String message;
    /**
     * 发送者编号
     */
    private String senderId;
    /**
     * 接收者编号
     */
    private String receiverId;
    /**
     * 消息编号，用于签收状态的变更
     */
    private String messageId;

}
