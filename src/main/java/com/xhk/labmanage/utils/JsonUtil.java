package com.xhk.labmanage.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by xhk on 18/3/4
 */
public class JsonUtil {

    /**
     * 根据json获取map
     * @param json
     * @return
     */
    public static Map getMapFromJson(String json){
        return JSON.parseObject(json,Map.class);
    }

    /**
     * 根据json获取指定对象
     * @param json
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getObjectFromJson(String json,Class<T> beanClass){
    		if(StringUtils.isBlank(json)){
    			return null;
    		}
        return JSON.parseObject(json,beanClass);
    }

    /**
     * 返回对象的jsonstr
     * @param obj
     * @return
     */
    public static String getJsonFromObject(Object obj){
        if(obj == null){
            return "";
        }
        if(obj instanceof List){
            return JSONArray.toJSONString(obj);
        }
        return JSON.toJSONString(obj);
    }

    //原始Map<String, Object>格式字符串
    public static Map json2Map(String str) throws Exception{
        try {
            Map<String, Object> ret = new HashMap<String, Object>();
            ret = JSON.parseObject(str.toString(), Map.class);
            return ret;
        } catch (Exception e) {
            throw new Exception("JSON[" + str + "] 解析到 Map<String, Object> 失败！");
        }
    }

    //原始List<Map<String, Object>>格式字符串
    public static List<Map<String, Object>> json2List(String str) throws Exception {
        try {
            List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
            JSONArray jsonArray = JSON.parseArray(str);

            for (Object object : jsonArray) {
                Map<String, Object> tmp = new HashMap<String, Object>();
                tmp = JSON.parseObject(object.toString(), Map.class);
                ret.add(tmp);
            }

            return ret;
        } catch (Exception e) {
            throw new Exception("JSON[" + str + "] 解析到 List<Map<String, Object>> 失败！");
        }
    }

    //原始List<String>格式
    public static List<String> json2List2(String str) throws Exception {
        try {
            List<String> ret = new ArrayList<String>();
            JSONArray jsonArray = JSON.parseArray(str);

            for (Object object : jsonArray) {
                ret.add(object.toString());
            }

            return ret;
        } catch (Exception e) {
            throw new Exception("JSON[" + str + "] 解析到 List<String> 失败！");
        }
    }
}

