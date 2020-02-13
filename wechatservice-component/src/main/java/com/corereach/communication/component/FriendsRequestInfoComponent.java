package com.corereach.communication.component;

import com.corereach.communication.common.domain.UserInfoDTO;

import java.util.List;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/13 18:43
 * @Version V1.0
 **/
public interface FriendsRequestInfoComponent {

    /**
     * 添加好友请求
     *
     * @param myUserId       我的用户编号
     * @param friendUsername 好友用户名
     * @return Boolean
     */
    Boolean addFriendsRequest(String myUserId, String friendUsername);

    /**
     * 获取请求加我为好友的用户信息
     *
     * @param myUserId 我的用户编号
     * @return List
     */
    List<UserInfoDTO> listUserInfoWithRequestInfo(String myUserId);

    /**
     * 根据发送者和接收者id删除好友请求信息
     *
     * @param sendUserId   发送者编号
     * @param acceptUserId 接收者编号
     * @return Integer
     */
    Boolean deleteFriendsRequestInfo(String sendUserId, String acceptUserId);

    /**
     * 通过或忽略好友请求
     *
     * @param sendUserId   请求者id
     * @param acceptUserId 接收者id
     * @param operatorType 操作类型 0-忽略，1-通过
     * @return Boolean
     */
    List<UserInfoDTO> passOrIgnoreRequest(String sendUserId, String acceptUserId, Integer operatorType);
}
