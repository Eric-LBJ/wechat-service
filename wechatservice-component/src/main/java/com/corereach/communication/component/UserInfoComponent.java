package com.corereach.communication.component;

import com.corereach.communication.common.domain.UserInfoDTO;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2019/12/23 14:58
 * @Version V1.0
 **/
public interface UserInfoComponent {

    /**
     * 根据用户编号获取用户信息
     *
     * @param userId 用户编号
     * @return UserInfoDTO
     */
    UserInfoDTO getUserInfoByUserId(String userId);

    /**
     * 判断用户是否存在
     *
     * @param username 用户名
     * @return Boolean
     */
    Boolean usernameIsExist(String username);

    /**
     * 用户登录或注册
     *
     * @param userInfoDTO 用户信息
     * @return UserInfoDTO
     */
    String registerOrLogin(UserInfoDTO userInfoDTO);

    /**
     * 用户登录时，校验用户名和密码
     *
     * @param username 用户名
     * @param password 密码
     * @return UserInfoDTO
     */
    UserInfoDTO checkPassword(String username, String password);

    /**
     * 用户注册
     *
     * @param userInfoDTO 用户信息
     * @return UserInfoDTO
     */
    UserInfoDTO insertUser(UserInfoDTO userInfoDTO);

    /**
     * 更新用户信息
     *
     * @param userInfoDTO 用户信息
     * @return Boolean
     */
    UserInfoDTO updateUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 根据用户账号获取用户信息
     *
     * @param myUserId       我的用户编号
     * @param friendUsername 用户账号
     * @return UserInfoDTO
     */
    UserInfoDTO searchUserInfoByUserName(String myUserId, String friendUsername);

    /**
     * 根据用户编号获取用户信息
     *
     * @param username 用户名
     * @return UserInfoDTO
     */
    UserInfoDTO getUserInfoByUsername(String username);
}
