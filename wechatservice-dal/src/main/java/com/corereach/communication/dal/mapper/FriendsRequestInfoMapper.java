package com.corereach.communication.dal.mapper;

import com.corereach.communication.dal.domain.FriendsRequestInfo;
import com.corereach.communication.dal.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ga.zhang
 * @date 2020/2/13 20:35:30
 */
@Mapper
public interface FriendsRequestInfoMapper {

    /**
     * 新增消息
     *
     * @param record 消息
     * @return Integer
     */
    Integer insert(FriendsRequestInfo record);

    /**
     * 根据发送者和接收者id获取信息
     *
     * @param sendUserId   发送者编号
     * @param acceptUserId 接收者编号
     * @return FriendsRequestInfo
     */
    FriendsRequestInfo selectBySendAndAcceptUserId(@Param("sendUserId") String sendUserId,
                                                   @Param("acceptUserId") String acceptUserId);

    /**
     * 根据发id获取信息
     *
     * @param id 发送者编号
     * @return FriendsRequestInfo
     */
    FriendsRequestInfo selectById(@Param("id") String id);

    /**
     * 获取请求加我为好友的用户信息
     *
     * @param myUserId 我的用户编号
     * @return List
     */
    List<UserInfo> listUserInfoWithRequestInfo(@Param("myUserId") String myUserId);

    /**
     * 根据发送者和接收者id删除好友请求信息
     *
     * @param sendUserId   发送者编号
     * @param acceptUserId 接收者编号
     * @return Integer
     */
    Integer deleteFriendsRequestInfo(@Param("sendUserId") String sendUserId,
                                     @Param("acceptUserId") String acceptUserId);

}