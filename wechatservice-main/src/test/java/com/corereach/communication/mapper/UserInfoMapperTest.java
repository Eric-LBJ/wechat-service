package com.corereach.communication.mapper;

import com.corereach.communication.WeChatApplicationTests;
import com.corereach.communication.dal.domain.UserInfo;
import com.corereach.communication.dal.mapper.UserInfoMapper;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/7 23:14
 * @Version V1.0
 **/
public class UserInfoMapperTest extends WeChatApplicationTests {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Test
    public void selectByPrimaryKeyTest() {
        UserInfo result = userInfoMapper
                .selectUserInfoByUsernameAndPassWord("1","1");

        System.out.println(result);
    }

}
