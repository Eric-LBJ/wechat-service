package com.corereach.communication.netty.comm;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @Description: 用户编号和channel关联处理
 * @Author ga.zhang
 * @Date 2020/2/18 20:02
 * @Version V1.0
 **/
@Slf4j
public class UserChannelRel {

    private static HashMap<String, Channel> manager = new HashMap<>();

    /**
     * 将channel与用户编号对应存储
     *
     * @param userId  用户编号
     * @param channel channel
     */
    public static void put(String userId, Channel channel) {
        manager.put(userId, channel);
    }

    /**
     * 通过用户编号获取channel
     *
     * @param userId 用户编号
     * @return Channel
     */
    public static Channel get(String userId) {
        return manager.get(userId);
    }

    public static void output() {
        for (HashMap.Entry<String, Channel> entry : manager.entrySet()) {
            log.info("UserId: {} , ChannelId: {}", entry.getKey(), entry.getValue().id().asLongText());
        }
    }

}
