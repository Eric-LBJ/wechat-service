package com.corereach.communication.dal.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : ga.zhang
 */
@Data
@ToString
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -1837587155324055009L;

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
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户头像
     */
    private String faceImage;
    /**
     * 大头像
     */
    private String faceImageBig;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 二维码
     */
    private String qrcode;
    /**
     * client_id,设备id
     */
    private String cid;
    /**
     * 逻辑删除
     */
    private Long isDeleted;
}