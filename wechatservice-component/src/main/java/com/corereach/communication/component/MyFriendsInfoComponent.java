package com.corereach.communication.component;

import com.corereach.communication.common.domain.MyFriendsInfoDTO;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/13 20:58
 * @Version V1.0
 **/
public interface MyFriendsInfoComponent {

    /**
     * 新增好友
     *
     * @param record 好友关联信息
     * @return Integer
     */
    Boolean insert(MyFriendsInfoDTO record);

    /**
     * 根据用户编号获取朋友账号信息
     *
     * @param myUserId       我的用户id
     * @param myFriendUserId 朋友的用户id
     * @return MyFriendsInfo
     */
    MyFriendsInfoDTO selectMyFriendsInfoByUserId(String myUserId, String myFriendUserId);

}
