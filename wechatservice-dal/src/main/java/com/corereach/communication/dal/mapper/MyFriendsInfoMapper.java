package com.corereach.communication.dal.mapper;

import com.corereach.communication.dal.domain.MyFriendsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ga.zhang
 * @date 2020/2/13 20:57:20
 */
@Mapper
public interface MyFriendsInfoMapper {

    /**
     * 删除好友
     *
     * @param id 主键id
     * @return Integer
     */
    Integer deleteByPrimaryKey(String id);

    /**
     * 新增好友
     *
     * @param record 好友关联信息
     * @return Integer
     */
    Integer insert(MyFriendsInfo record);

    /**
     * 根据主键id查询好友
     *
     * @param id 主键id
     * @return MyFriendsInfo
     */
    MyFriendsInfo selectByPrimaryKey(String id);

    /**
     * 查询所有好友
     *
     * @return List
     */
    List<MyFriendsInfo> selectAll();

    /**
     * 根据用户编号获取朋友账号信息
     *
     * @param myUserId       我的用户id
     * @param myFriendUserId 朋友的用户id
     * @return MyFriendsInfo
     */
    MyFriendsInfo selectMyFriendsInfoByUserId(@Param("myUserId") String myUserId, @Param("myFriendUserId") String myFriendUserId);
}