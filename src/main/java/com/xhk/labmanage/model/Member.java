package com.xhk.labmanage.model;

/**
 * create by xhk on 2018/3/26
 */
public class Member {
    private Integer id;
    private String name;
    private String ename;
    private String head_url;
    private Integer status; //1：老师 2：博士生 3：硕士生
    private String note;
    private String enote;
    private String content;
    private String econtent;
    private String content_url;
    private Integer enterTime;
    private Integer graduateTime; //空为在读生， 非空为毕业生
    private Integer createTime;
    private Integer updateTime;


    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

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

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
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

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
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
