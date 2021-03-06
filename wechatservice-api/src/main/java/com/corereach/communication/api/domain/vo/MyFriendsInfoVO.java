package com.corereach.communication.api.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : ga.zhang
 */
@Data
@ToString
public class MyFriendsInfoVO implements Serializable {

    private static final long serialVersionUID = 1720449136177785056L;

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
     * 我的用户id
     */
    private String myUserId;
    /**
     * 朋友的用户id
     */
    private String myFriendUserId;
    /**
     * 逻辑删除
     */
    private Long isDeleted;
}