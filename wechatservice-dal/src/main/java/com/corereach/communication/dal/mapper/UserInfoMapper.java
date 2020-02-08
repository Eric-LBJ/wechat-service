package com.corereach.communication.dal.mapper;

import com.corereach.communication.dal.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ga.zhang
 */
@Mapper
public interface UserInfoMapper {

    /**
     * 新增用户
     * @param record 用户信息
     * @return int
     */
    int insert(UserInfo record);

    /**
     * 根据用户编号获取用户信息
     * @param id 用户编号
     * @return UserInfo
     */
    UserInfo selectByUserId(String id);

    /**
     * 根据用户编号更新用户信息
     * @param record 用户信息
     * @return int
     */
    int updateByUserId(UserInfo record);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return UserInfo
     */
    UserInfo selectUserInfoByUsername(@Param("username") String username);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @param password 密码
     * @return UserInfo
     */
    UserInfo selectUserInfoByUsernameAndPassWord(@Param("username") String username,@Param("password") String password);

}