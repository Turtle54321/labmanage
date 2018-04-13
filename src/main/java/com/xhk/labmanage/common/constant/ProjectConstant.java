package com.xhk.labmanage.common.constant;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * create by xhk on 18/3/2
 */
public class ProjectConstant {

    public static String INDEX_IMG_DIR;

    public static String MEMBER_IMG_DIR;

    public static String NEWS_IMG_DIR;

    //用File.separator便于跨平台
    public static String IMG_SAVE_DIR = "src"+ File.separator+"main"+ File.separator+"webapp"+ File.separator+"static"+File.separator+"base"+File.separator+"img";

    private static Properties p = new Properties();

    static {

        try {
            InputStream in = ProjectConstant.class.getResourceAsStream("/dir.properties");//这里有人用new FileInputStream(fileName),不过这种方式找不到配置文件。有人说是在classes下，我调过了，不行。
            p.load(in);
            in.close();

            INDEX_IMG_DIR = p.getProperty("index.img.dir");
            MEMBER_IMG_DIR = p.getProperty("member.img.dir");
            NEWS_IMG_DIR = p.getProperty("news.img.dir");
//            new String(p.getProperty("").getBytes("ISO-8859-1"), "UTF-8");//中文转码的

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
