package com.corereach.communication.netty.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/18 19:32
 * @Version V1.0
 **/
@Data
@ToString
public class DataContent implements Serializable {

    private static final long serialVersionUID = -8568307400026303570L;

    /**
     * 动作类型
     */
    private Integer action;
    /**
     * 聊天内容
     */
    private ChatMessage chatMessage;
    /**
     * 扩展字段
     */
    private String extend;
}
