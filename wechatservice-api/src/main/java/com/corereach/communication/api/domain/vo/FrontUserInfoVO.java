package com.corereach.communication.api.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/1/13 14:15
 * @Version V1.0
 **/
@Data
@ToString
public class FrontUserInfoVO implements Serializable {

    private static final long serialVersionUID = 7805243804853710565L;

    /**
     * 用户编号
     */
    private String id;
    /**
     * 用户名
     */
    private String username;
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
}
