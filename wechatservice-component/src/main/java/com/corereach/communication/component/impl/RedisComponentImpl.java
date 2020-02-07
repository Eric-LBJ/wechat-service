package com.corereach.communication.component.impl;

import com.corereach.communication.component.RedisComponent;
import com.corereach.communication.dal.redis.RedisDAO;
import com.icode.rich.comm.ServiceSupport;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/7 23:11
 * @Version V1.0
 **/
@Component
public class RedisComponentImpl extends ServiceSupport implements RedisComponent {

    @Resource
    private RedisDAO redisDAO;

    @Override
    public String get(String key) {
        return redisDAO.get(key);
    }

    @Override
    public List<String> mGet(String... keys) {
        return redisDAO.mGet(keys);
    }

    @Override
    public Boolean set(String key, String value, Integer outTime) {
        return redisDAO.set(key, value, outTime);
    }

    @Override
    public Boolean exists(String key) {
        return redisDAO.exists(key);
    }

    @Override
    public Boolean del(String key) {
        return redisDAO.del(key);
    }

    @Override
    public Long incr(String key) {
        return redisDAO.incr(key);
    }

    @Override
    public Set<String> getAllKey(String prefix) {
        return redisDAO.getAllKey(prefix);
    }

    @Override
    public Integer expire(String key, Integer seconds) {
        return redisDAO.expire(key, seconds);
    }

}
