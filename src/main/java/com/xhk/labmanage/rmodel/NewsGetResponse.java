package com.xhk.labmanage.rmodel;

import com.xhk.labmanage.model.News;

import java.util.List;

/**
 * create by xhk on 2018/4/15
 */
public class NewsGetResponse {
    private List<News> newsList;
    private Integer totalNum; //总条数

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }
}
