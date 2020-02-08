package com.corereach.communication.service;

import com.corereach.communication.api.UserInfoService;
import com.corereach.communication.api.domain.bo.UserInfoBO;
import com.corereach.communication.api.domain.vo.FrontUserInfoVO;
import com.corereach.communication.api.domain.vo.UserInfoVO;
import com.corereach.communication.common.comm.Constants;
import com.corereach.communication.common.domain.UserInfoDTO;
import com.corereach.communication.common.utils.ConvertUtil;
import com.corereach.communication.component.UserInfoComponent;
import com.icode.rich.comm.AiCodes;
import com.icode.rich.comm.AiResult;
import com.icode.rich.comm.ServiceSupport;
import com.icode.rich.exception.AiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2019/12/23 14:56
 * @Version V1.0
 **/
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceSupport implements UserInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Resource
    private UserInfoComponent userInfoComponent;

    @Override
    public UserInfoVO getUserInfoByUserId(String userId) {
        return ConvertUtil.convertDomain(UserInfoVO.class, userInfoComponent.getUserInfoByUserId(userId));
    }

    @Override
    public Boolean usernameIsExist(String username) {
        return userInfoComponent.usernameIsExist(username);
    }

    @Override
    public AiResult<String> registerOrLogin(UserInfoVO user) {
        /**
         * 数据校验
         */
        super.notNull("用户信息", user)
                .notNull("账号", user.getUsername())
                .notBlank("账号", user.getUsername())
                .checkLength("账号", user.getUsername(), 1, 64)
                .notNull("密码", user.getPassword())
                .notBlank("密码", user.getPassword())
                .checkLength("密码", user.getPassword(), 1, 64);
        try {
            return new AiResult<>(Constants.isGlobal, userInfoComponent.registerOrLogin(ConvertUtil.convertDomain(UserInfoDTO.class, user)));
        } catch (AiException e) {
            LOGGER.error(e.getMessage(), e);
            return new AiResult<>(Constants.isGlobal, e.getCode(), e.getMessage(), e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new AiResult<>(Constants.isGlobal, AiCodes.SYSTEM_ERROR);
        }
    }

    @Override
    public FrontUserInfoVO checkPassword(String username, String password) {
        return ConvertUtil.convertDomain(FrontUserInfoVO.class, userInfoComponent.checkPassword(username, password));
    }

    @Override
    public FrontUserInfoVO insertUser(UserInfoVO user) {
        return ConvertUtil.convertDomain(FrontUserInfoVO.class,
                userInfoComponent.insertUser(ConvertUtil.convertDomain(UserInfoDTO.class, user)));
    }

    @Override
    public AiResult<UserInfoVO> updateUserInfo(UserInfoBO user) {
        /**
         * 数据校验
         */
        super.notNull("用户信息", user)
                .notNull("用户编号", user.getId())
                .notBlank("用户编号", user.getId())
                .checkLength("用户编号", user.getId(), 1, 64);
        try {
            UserInfoVO result = ConvertUtil.convertDomain(UserInfoVO.class,
                    userInfoComponent.updateUserInfo(ConvertUtil.convertDomain(UserInfoDTO.class, user)));
            return new AiResult<>(Constants.isGlobal, result);
        } catch (AiException e) {
            LOGGER.error(e.getMessage(), e);
            return new AiResult<>(Constants.isGlobal, e.getCode(), e.getMessage(), e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new AiResult<>(Constants.isGlobal, AiCodes.SYSTEM_ERROR);
        }
    }

    @Override
    public AiResult<FrontUserInfoVO> searchUserInfoByUserName(String myUserId, String friendUsername) {
        /**
         * 数据校验
         */
        super.notNull("用户编号", myUserId)
                .notBlank("用户编号", myUserId)
                .checkLength("用户编号", myUserId, 1, 64)
                .notNull("账号", friendUsername)
                .notBlank("账号", friendUsername)
                .checkLength("账号", friendUsername, 1, 64);
        try {
            FrontUserInfoVO result = ConvertUtil.convertDomain(FrontUserInfoVO.class,
                    userInfoComponent.searchUserInfoByUserName(myUserId, friendUsername));
            return new AiResult<>(Constants.isGlobal, result);
        } catch (AiException e) {
            LOGGER.error(e.getMessage(), e);
            return new AiResult<>(Constants.isGlobal, e.getCode(), e.getMessage(), e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new AiResult<>(Constants.isGlobal, AiCodes.SYSTEM_ERROR);
        }
    }
}
