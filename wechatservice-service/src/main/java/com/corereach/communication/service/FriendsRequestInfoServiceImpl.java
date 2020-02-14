package com.corereach.communication.service;

import com.corereach.communication.api.FriendsRequestInfoService;
import com.corereach.communication.api.domain.vo.UserInfoVO;
import com.corereach.communication.common.comm.Constants;
import com.corereach.communication.common.domain.UserInfoDTO;
import com.corereach.communication.common.utils.ConvertUtil;
import com.corereach.communication.component.FriendsRequestInfoComponent;
import com.icode.rich.comm.AiCodes;
import com.icode.rich.comm.AiResult;
import com.icode.rich.comm.RangeQueryResult;
import com.icode.rich.comm.ServiceSupport;
import com.icode.rich.exception.AiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/13 18:40
 * @Version V1.0
 **/
@Service("friendsRequestInfoService")
public class FriendsRequestInfoServiceImpl extends ServiceSupport implements FriendsRequestInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendsRequestInfoServiceImpl.class);

    @Resource
    private FriendsRequestInfoComponent friendsRequestInfoComponent;

    @Override
    public AiResult<Boolean> addFriendsRequest(String myUserId, String friendUsername) {
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
            Boolean result = friendsRequestInfoComponent.addFriendsRequest(myUserId, friendUsername);
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
    public AiResult<RangeQueryResult<UserInfoVO>> listUserInfoWithRequestInfo(String myUserId) {
        /**
         * 数据校验
         */
        super.notNull("用户编号", myUserId)
                .notBlank("用户编号", myUserId)
                .checkLength("用户编号", myUserId, 1, 64);
        try {
            return new AiResult<>(Constants.isGlobal, packageResult(friendsRequestInfoComponent.listUserInfoWithRequestInfo(myUserId)));
        } catch (AiException e) {
            LOGGER.error(e.getMessage(), e);
            return new AiResult<>(Constants.isGlobal, e.getCode(), e.getMessage(), e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new AiResult<>(Constants.isGlobal, AiCodes.SYSTEM_ERROR);
        }
    }

    @Override
    public AiResult<RangeQueryResult<UserInfoVO>> passOrIgnoreRequest(String sendUserId, String acceptUserId, Integer operatorType) {
        /**
         * 数据校验
         */
        super.notNull("请求者编号", sendUserId)
                .notBlank("请求者编号", sendUserId)
                .checkLength("请求者编号", sendUserId, 1, 64)
                .notNull("接收者编号", acceptUserId)
                .notBlank("接收者编号", acceptUserId)
                .checkLength("接收者编号", acceptUserId, 1, 64)
                .notNull("操作类型", operatorType)
                .checkEnums("操作类型", operatorType, Constants.OPERATOR_TYPE_LIST);
        try {
            return new AiResult<>(Constants.isGlobal,
                    packageResult(friendsRequestInfoComponent.passOrIgnoreRequest(sendUserId, acceptUserId, operatorType)));
        } catch (AiException e) {
            LOGGER.error(e.getMessage(), e);
            return new AiResult<>(Constants.isGlobal, e.getCode(), e.getMessage(), e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new AiResult<>(Constants.isGlobal, AiCodes.SYSTEM_ERROR);
        }
    }

    private RangeQueryResult<UserInfoVO> packageResult(List<UserInfoDTO> userInfoDTOList) {
        RangeQueryResult<UserInfoVO> result = new RangeQueryResult<>();
        List<UserInfoVO> userInfoVOList = new ArrayList<>();
        userInfoDTOList.forEach(item -> userInfoVOList.add(ConvertUtil.convertDomain(UserInfoVO.class, item)));
        result.setData(userInfoVOList);
        result.setTotalNum((long) userInfoVOList.size());
        return result;
    }
}
