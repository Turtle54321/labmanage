package com.xhk.labmanage.utils;

import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * create by xhk on 18/2/27
 */
public class StringUtil {
    /**
     * 将Map转化为URI,安全uri
     */
    public static String getUriFromMap(Map<String, String> params) throws Exception {
        try {
            if(CollectionUtils.isEmpty(params)){
                return "";
            }
            String str_uri = "";
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey().toString();
                String value = URLEncoder.encode(String.valueOf(entry.getValue()), "utf-8");
                if (!"".equals(value) && value != null) {
                    str_uri = str_uri + key + "=" + value + "&";
                }
            }
            if(org.springframework.util.StringUtils.isEmpty(str_uri)){
                return str_uri;
            }
            str_uri = str_uri.substring(0, str_uri.length() - 1);
            return str_uri;
        } catch (UnsupportedEncodingException e) {
            throw e;
        }
    }
}
