package com.xhk.labmanage.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * create by xhk on 18/3/4
 */
public class ThreadVariableUtil {
    private static ThreadLocal<Map> local = new ThreadLocal<>();

    public static Map getLocalMap(){
        return local.get();
    }

    public static void putThreadVariable(String key,Object obj){
        Map errMap = getLocalMap();
        if(errMap == null){
            errMap = new HashMap();
        }
        errMap.put(key,obj);
        local.set(errMap);
    }

    public static void removeThreadVariableMap(){
        local.remove();
    }

    public static Object getThreadVariable(String key){
        Map errMap = getLocalMap();
        if(errMap == null){
            return null;
        }
        return errMap.get(key);
    }

    public static Object rmThreadVariable(String key){
        Map errMap = getLocalMap();
        if(errMap == null){
            return null;
        }
        return errMap.remove(key);
    }

    public static Long getPlatformNo(){
        //String platformNo = ThreadVariableUtil.getThreadVariable("PlatformNo");
        return 0l;
    }
}
