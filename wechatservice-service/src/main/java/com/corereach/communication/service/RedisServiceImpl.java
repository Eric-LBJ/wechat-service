package com.corereach.communication.service;

import com.alibaba.fastjson.JSONObject;
import com.corereach.communication.api.RedisService;
import com.corereach.communication.common.comm.Constants;
import com.corereach.communication.component.RedisComponent;
import com.icode.rich.comm.AiCodes;
import com.icode.rich.comm.AiResult;
import com.icode.rich.comm.ServiceSupport;
import com.icode.rich.exception.AiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/8 15:20
 * @Version V1.0
 **/
@Service("redisService")
public class RedisServiceImpl extends ServiceSupport implements RedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Resource
    private RedisComponent redisComponent;

    @Override
    public AiResult<Map> checkToken(String token) {

        super.notNull("token",token)
                .notBlank("token",token)
                .checkLength("token",token,1,64);
        try {
            Map<String,Object> result = new HashMap<>(7);
            String tokenKey = token + Constants.ISOLATION + Constants.ISOLATION + Constants.ISOLATION;
            String value = redisComponent.get(tokenKey);
            JSONObject jsonObject = JSONObject.parseObject(value);
            result.put("userId",jsonObject.get("id"));
            result.put("username",jsonObject.get("username"));
            result.put("nickName",jsonObject.get("nickName"));
            result.put("faceImage",jsonObject.get("faceImage"));
            result.put("faceImageBig",jsonObject.get("faceImageBig"));
            result.put("qrcode",jsonObject.get("qrcode"));
            result.put("cid",jsonObject.get("cid"));
            return new AiResult<>(Constants.isGlobal,result);
        } catch (AiException e) {
            LOGGER.error(e.getMessage(), e);
            return new AiResult<>(Constants.isGlobal, e.getCode(), e.getMessage(), e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new AiResult<>(Constants.isGlobal, AiCodes.SYSTEM_ERROR);
        }

    }
}
