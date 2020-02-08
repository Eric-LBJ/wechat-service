package com.corereach.communication.component;

import com.corereach.communication.WeChatApplicationTests;
import com.corereach.communication.api.UserInfoService;
import com.corereach.communication.api.domain.vo.FrontUserInfoVO;
import com.icode.rich.comm.AiResult;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/7 23:14
 * @Version V1.0
 **/
public class RedisComponentTest extends WeChatApplicationTests {

    @Resource
    private RedisComponent redisComponent;

    @Test
    public void selectByPrimaryKeyTest() {
        String result = redisComponent.get("01000a39789b4cf99d947870149a86e4|||");
        System.out.println(result);
    }

}
