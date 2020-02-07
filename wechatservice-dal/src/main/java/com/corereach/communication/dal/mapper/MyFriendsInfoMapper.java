package com.corereach.communication.dal.mapper;

import com.corereach.communication.dal.domain.MyFriendsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyFriendsInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(MyFriendsInfo record);

    MyFriendsInfo selectByPrimaryKey(String id);

    List<MyFriendsInfo> selectAll();

    int updateByPrimaryKey(MyFriendsInfo record);

    /**
     * 根据用户编号获取朋友账号信息
     *
     * @param myUserId       我的用户id
     * @param myFriendUserId 朋友的用户id
     * @return MyFriendsInfo
     */
    MyFriendsInfo selectMyFriendsInfoByUserId(@Param("myUserId") String myUserId, @Param("myFriendUserId") String myFriendUserId);
}