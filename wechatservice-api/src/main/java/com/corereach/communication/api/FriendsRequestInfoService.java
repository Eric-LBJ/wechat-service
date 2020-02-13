package com.corereach.communication.api;

import com.corereach.communication.api.domain.vo.UserInfoVO;
import com.icode.rich.comm.AiResult;
import com.icode.rich.comm.RangeQueryResult;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/13 18:37
 * @Version V1.0
 **/
public interface FriendsRequestInfoService {

    /**
     * 添加好友请求
     *
     * @param myUserId       我的用户编号
     * @param friendUsername 好友用户名
     * @return AiResult<Boolean>
     */
    AiResult<Boolean> addFriendsRequest(String myUserId, String friendUsername);

    /**
     * 获取请求加我为好友的用户信息
     *
     * @param myUserId 我的用户编号
     * @return List
     */
    AiResult<RangeQueryResult<UserInfoVO>> listUserInfoWithRequestInfo(String myUserId);

    /**
     * 通过或忽略好友请求
     *
     * @param sendUserId   请求者id
     * @param acceptUserId 接收者id
     * @param operatorType 操作类型 0-忽略，1-通过
     * @return Boolean
     */
    AiResult<Boolean> passOrIgnoreRequest(String sendUserId,String acceptUserId,Integer operatorType);

}
