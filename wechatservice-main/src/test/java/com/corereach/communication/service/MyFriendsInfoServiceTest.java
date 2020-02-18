package com.corereach.communication.service;

import com.corereach.communication.WeChatApplicationTests;
import com.corereach.communication.api.FriendsRequestInfoService;
import com.corereach.communication.api.MyFriendsInfoService;
import com.corereach.communication.api.domain.vo.UserInfoVO;
import com.icode.rich.comm.AiResult;
import com.icode.rich.comm.RangeQueryResult;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/7 22:28
 * @Version V1.0
 **/
public class MyFriendsInfoServiceTest extends WeChatApplicationTests {

    @Resource
    private MyFriendsInfoService myFriendsInfoService;

    @Test
    public void searchUserInfoByUserNameTest() {
        AiResult<RangeQueryResult<UserInfoVO>> result = myFriendsInfoService
                .listUserInfoWithFriendsInfo("200118CHNX46H9S8");
        System.out.println(result);
    }

}
