package com.corereach.communication.api;

import com.icode.rich.comm.AiResult;

import java.util.Map;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/8 15:17
 * @Version V1.0
 **/
public interface RedisService {

    /**
     * 根据token信息换取其他信息
     * @param token token
     * @return map
     */
    AiResult<Map> checkToken(String token);

}
