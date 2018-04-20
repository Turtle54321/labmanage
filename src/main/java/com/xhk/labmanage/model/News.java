package com.xhk.labmanage.model;

/**
 * create by xhk on 2018/3/26
 */
public class News {
    public static Integer NEWS_TYPE = 1;
    public static Integer PROJECT_TPYE = 2;
    public static Integer PHOTO_TYPE = 3;

    public static boolean typeConfirm(Integer type){
        // 判断type是否合法
        if (type == NEWS_TYPE || type == PROJECT_TPYE || type == PHOTO_TYPE){
            return true;
        }
        else{
            return false;
        }
    }

    private Integer id;
    private String title;
    private String etitle;
    private String content;
    private String econtent;
    private String url;
    private Integer type; // 1为新闻，2为项目，3为相册
    private Integer createTime;
    private Integer updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEtitle() {
        return etitle;
    }

    public void setEtitle(String etitle) {
        this.etitle = etitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEcontent() {
        return econtent;
    }

    public void setEcontent(String econtent) {
        this.econtent = econtent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }
}
