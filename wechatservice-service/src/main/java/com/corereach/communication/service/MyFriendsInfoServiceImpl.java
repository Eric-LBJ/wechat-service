package com.corereach.communication.service;

import com.corereach.communication.api.MyFriendsInfoService;
import com.corereach.communication.api.domain.vo.UserInfoVO;
import com.corereach.communication.common.comm.Constants;
import com.corereach.communication.common.domain.UserInfoDTO;
import com.corereach.communication.common.utils.ConvertUtil;
import com.corereach.communication.component.MyFriendsInfoComponent;
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
 * @Date 2020/2/14 13:44
 * @Version V1.0
 **/
@Service("myFriendsInfoService")
public class MyFriendsInfoServiceImpl extends ServiceSupport implements MyFriendsInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyFriendsInfoServiceImpl.class);

    @Resource
    private MyFriendsInfoComponent myFriendsInfoComponent;

    @Override
    public AiResult<RangeQueryResult<UserInfoVO>> listUserInfoWithFriendsInfo(String myUserId) {
        /**
         * 数据校验
         */
        super.notNull("用户编号", myUserId)
                .notBlank("用户编号", myUserId)
                .checkLength("用户编号", myUserId, 1, 64);
        try {
            List<UserInfoDTO> userInfoDTOList = myFriendsInfoComponent.listUserInfoWithFriendsInfo(myUserId);
            List<UserInfoVO> userInfoVOList = new ArrayList<>();
            userInfoDTOList.forEach(item -> userInfoVOList.add(ConvertUtil.convertDomain(UserInfoVO.class, item)));
            RangeQueryResult<UserInfoVO> result = new RangeQueryResult<>();
            result.setData(userInfoVOList);
            result.setTotalNum((long) userInfoVOList.size());
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
