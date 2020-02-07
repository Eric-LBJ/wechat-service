package com.corereach.communication.dal.redis.impl;

import com.corereach.communication.dal.redis.RedisDAO;
import com.icode.rich.comm.ServiceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;


/**
 * @author ga.zhang
 * @date 2020/2/7 17:01
 */
@Component
public class RedisDAOImpl extends ServiceSupport implements RedisDAO {

    private static final Logger logger = LoggerFactory.getLogger(RedisDAOImpl.class);

    private final JedisPool jedisPool;

    public RedisDAOImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public String get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("redis连接池异常" + e.getMessage());
            return null;
        }
    }

    @Override
    public List<String> mGet(String... keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.mget(keys);
        } catch (Exception e) {
            logger.error("redis连接池异常" + e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean set(String key, String value, Integer outTime) {
        try (Jedis jedis = jedisPool.getResource()) {
            if (StringUtils.isEmpty(value)) {
                return false;
            }
            //过期时间
            int seconds = Integer.parseInt(outTime.toString());
            if (seconds <= 0) {
                jedis.set(key, value);
            } else {
                jedis.setex(key, seconds, value);
            }
            return true;
        } catch (Exception e) {
            logger.error("redis连接池异常" + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        } catch (Exception e) {
            logger.error("redis连接池异常" + e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean del(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            if (jedis.del(key) > 0) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } catch (Exception e) {
            logger.error("redis连接池异常" + e.getMessage());
            return null;
        }
    }

    @Override
    public Long incr(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.incr(key);
        } catch (Exception e) {
            logger.error("redis连接池异常" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Set<String> getAllKey(String prefix) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.keys(prefix);
        } catch (Exception e) {
            logger.error("redis连接池异常" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Integer expire(String key, Integer seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.expire(key, seconds).intValue();
        } catch (Exception e) {
            logger.error("redis连接池异常" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
