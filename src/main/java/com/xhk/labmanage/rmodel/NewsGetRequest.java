package com.xhk.labmanage.rmodel;

/**
 * create by xhk on 2018/4/15
 */
public class NewsGetRequest {
    private Integer page; //请求的页码 从0开始
    private Integer num; //一页的数量

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
