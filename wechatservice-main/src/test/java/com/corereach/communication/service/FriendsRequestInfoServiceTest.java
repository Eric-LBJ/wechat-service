package com.corereach.communication.service;

import com.corereach.communication.WeChatApplicationTests;
import com.corereach.communication.api.FriendsRequestInfoService;
import com.corereach.communication.api.UserInfoService;
import com.corereach.communication.api.domain.vo.FrontUserInfoVO;
import com.corereach.communication.api.domain.vo.UserInfoVO;
import com.icode.rich.comm.AiResult;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/7 22:28
 * @Version V1.0
 **/
public class FriendsRequestInfoServiceTest extends WeChatApplicationTests {

    @Resource
    private FriendsRequestInfoService friendsRequestInfoService;

    @Test
    public void searchUserInfoByUserNameTest() {
        AiResult<Boolean> result = friendsRequestInfoService
                .addFriendsRequest("200118CHNX46H9S8","Eric");
        System.out.println(result);
    }

}
