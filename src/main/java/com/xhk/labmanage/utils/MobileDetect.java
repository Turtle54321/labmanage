package com.xhk.labmanage.utils;

/**
 * create by xhk on 18/3/4
 */
public class MobileDetect {

    /**
     * 判断是否是移动端页面
     *
     * @param userAgent
     * @return
     */
    public static boolean isMobile(String userAgent) {
        return userAgent.toLowerCase().matches("(.*)(iphone|ipad|ipod|mobile|webview|android|tablet|phone|mqqrowser)(.*)");
    }

}
