package com.corereach.communication.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2019/12/19 13:59
 * @Version V1.0
 **/
public class ConvertUtil {

    /**
     * 基本对象转换
     *
     * @param targetClazz 目标对象类型
     * @param initObject 初始对象
     * @return T
     */
    public static <T> T convertDomain(Class<T> targetClazz, Object initObject) {
        Object targetObject = null;
        try {
            targetObject = targetClazz.newInstance();
            if (!ObjectUtils.isEmpty(initObject)) {
                BeanUtils.copyProperties(initObject, targetObject);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (targetClazz.isInstance(targetObject)) {
            return targetClazz.cast(targetObject);
        }
        return null;
    }

}
