package com.corereach.communication.dal.mapper;

import com.corereach.communication.dal.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserInfo record);

    UserInfo selectByPrimaryKey(String id);

    List<UserInfo> selectAll();

    int updateByPrimaryKey(UserInfo record);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return UserInfo
     */
    UserInfo selectUserInfoByUsername(@Param("username") String username);
}