package com.corereach.communication.api;

import com.corereach.communication.api.domain.vo.UserInfoVO;
import com.icode.rich.comm.AiResult;
import com.icode.rich.comm.RangeQueryResult;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/14 13:43
 * @Version V1.0
 **/
public interface MyFriendsInfoService {

    /**
     * 获取我的好友的用户信息
     * @param myUserId 我的用户编号
     * @return List
     */
    AiResult<RangeQueryResult<UserInfoVO>> listUserInfoWithFriendsInfo(String myUserId);

}
