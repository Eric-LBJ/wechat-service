package com.corereach.communication.dal.redis;

import java.util.List;
import java.util.Set;

/**
 * @author ga.zhang
 * @date 2020/2/7 17:01
 */
public interface RedisDAO {

    /**
     * 根据键通过get方法获取键对应值
     * @param key key值
     * @return String
     */
    String get(String key);

    /**
     * 通过过个key从redis中查询内容
     *
     * @param keys key值数组
     * @return List
     */
    List<String> mGet(String... keys);

    /**
     * 通过set方法向缓存中插入新的键值对
     *
     * @param key     key值
     * @param value   值
     * @param outTime 过期时间
     * @return Boolean
     */
    Boolean set(String key, String value, Integer outTime);

    /**
     * 通过键判断 key-value 是否存在
     *
     * @param key key值
     * @return Boolean
     */
    Boolean exists(String key);

    /**
     * 删除指定的key
     *
     * @param key key值
     * @return Boolean
     */
    Boolean del(String key);

    /**
     * 值自增
     *
     * @param key key值
     * @return Long
     */
    Long incr(String key);

    /**
     * 根据前缀获取key值
     *
     * @param prefix 前缀
     * @return Set
     */
    Set<String> getAllKey(String prefix);

    /**
     * 针对指定的key设定过期时间
     *
     * @param key     key值
     * @param seconds 时间
     * @return Integer
     */
    Integer expire(String key, Integer seconds);
}
