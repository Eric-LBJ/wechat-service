package com.corereach.communication.common.utils;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ga.zhang
 * @Description: 自定义响应结构, 转换类
 */
public class JsonUtil {

    /**定义jackson对象*/
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data 数据
     * @return String
     */
    public static String objectToJson(Object data) {
    	try {
            return MAPPER.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 将json结果集转化为对象
     * 
     * @param jsonData json数据
     * @param clazz 对象中的object类型
     * @return T
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> clazz) {
        try {
            return MAPPER.readValue(jsonData, clazz);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData json数据
     * @param clazz 类
     * @return List<T>
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> clazz) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
    	try {
            return MAPPER.readValue(jsonData, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
}
