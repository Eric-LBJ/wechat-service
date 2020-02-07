package com.corereach.communication.api.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :ga.zhang
 */
@Data
@ToString
public class FriendsRequestInfoVO implements Serializable {

    private static final long serialVersionUID = -5700879794042576099L;

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
     * 发消息时间
     */
    private Date requestDateTime;
    /**
     * 逻辑删除
     */
    private Long isDeleted;
}