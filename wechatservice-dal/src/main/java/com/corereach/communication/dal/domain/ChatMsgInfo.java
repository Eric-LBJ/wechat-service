package com.corereach.communication.dal.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :ga.zhang
 */
@Data
@ToString
public class ChatMsgInfo implements Serializable {

    private static final long serialVersionUID = -7018420825603098022L;

    /**
     * 主键
     */
    private String id;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 最新修改时间
     */
    private Date gmtModified;
    /**
     * 发送者用户id
     */
    private String sendUserId;
    /**
     * 接收者用户id
     */
    private String acceptUserId;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 签收状态：已读，未读
     */
    private Integer signFlag;
    /**
     * 逻辑删除
     */
    private Long isDeleted;
}