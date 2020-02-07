package com.corereach.communication.api.domain.bo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : ga.zhang
 */
@Data
@ToString
public class UserInfoBO implements Serializable {

    private static final long serialVersionUID = -2372000114719621133L;

    /**
     * 用户编号
     */
    private String id;

    /**
     * 用户头像
     */
    private String faceData;

    /**
     * 用户昵称
     */
    public String nickName;

}