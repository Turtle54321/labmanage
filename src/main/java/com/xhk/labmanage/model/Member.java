package com.xhk.labmanage.model;

/**
 * create by xhk on 2018/3/26
 */
public class Member {
    private Integer id;
    private String name;
    private String ename;
    private String headUrl;
    private Integer status; //1：老师 2：博士生 3：硕士生
    private String note;
    private String enote;
    private String content;
    private String econtent;
    private String contentUrl;
    private Integer enterTime;
    private Integer graduateTime; //空为在读生， 非空为毕业生
    private Integer createTime;
    private Integer updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEnote() {
        return enote;
    }

    public void setEnote(String enote) {
        this.enote = enote;
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

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public Integer getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Integer enterTime) {
        this.enterTime = enterTime;
    }

    public Integer getGraduateTime() {
        return graduateTime;
    }

    public void setGraduateTime(Integer graduateTime) {
        this.graduateTime = graduateTime;
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
