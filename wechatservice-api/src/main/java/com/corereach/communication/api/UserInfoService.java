package com.corereach.communication.api;

import com.corereach.communication.api.domain.bo.UserInfoBO;
import com.corereach.communication.api.domain.vo.FrontUserInfoVO;
import com.corereach.communication.api.domain.vo.UserInfoVO;
import com.icode.rich.comm.AiResult;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2019/12/23 14:55
 * @Version V1.0
 **/
public interface UserInfoService {

    /**
     * 根据用户名获取用户信息
     *
     * @param userId 用户编号
     * @return UserInfoVO
     */
    UserInfoVO getUserInfoByUserId(String userId);

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return Boolean
     */
    Boolean usernameIsExist(String username);

    /**
     * 登录或注册
     *
     * @param user 用户信息
     * @return AiResult<FrontUserInfoVO>
     */
    AiResult<String> registerOrLogin(UserInfoVO user);

    /**
     * 校验用户名和密码
     *
     * @param username 用户名
     * @param password 密码
     * @return UserInfoVO
     */
    FrontUserInfoVO checkPassword(String username, String password);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return UserInfoVO
     */
    FrontUserInfoVO insertUser(UserInfoVO user);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return Boolean
     */
    AiResult<UserInfoVO> updateUserInfo(UserInfoBO user);

    /**
     * 搜索用户信息
     *
     * @param myUserId       我的用户编号
     * @param friendUsername 好友用户名
     * @return AiResult<UserInfoVO>
     */
    AiResult<FrontUserInfoVO> searchUserInfoByUserName(String myUserId, String friendUsername);
}
