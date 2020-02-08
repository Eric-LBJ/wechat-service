package com.corereach.communication.service;

import com.corereach.communication.WeChatApplicationTests;
import com.corereach.communication.api.RedisService;
import com.corereach.communication.component.RedisComponent;
import com.icode.rich.comm.AiResult;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/7 23:14
 * @Version V1.0
 **/
public class RedisServiceTest extends WeChatApplicationTests {

    @Resource
    private RedisService redisService;

    @Test
    public void selectByPrimaryKeyTest() {
        AiResult<Map> result = redisService.checkToken("01000a39789b4cf99d947870149a86e4");
        System.out.println(result);
    }

}
