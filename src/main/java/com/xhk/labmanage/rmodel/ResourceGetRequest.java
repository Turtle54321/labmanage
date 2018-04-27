package com.xhk.labmanage.rmodel;

/**
 * create by xhk on 2018/4/26
 */
public class ResourceGetRequest {
    private Integer whichPage;
    private Integer perCount;

    public Integer getWhichPage() {
        return whichPage;
    }

    public void setWhichPage(Integer whichPage) {
        this.whichPage = whichPage;
    }

    public Integer getPerCount() {
        return perCount;
    }

    public void setPerCount(Integer perCount) {
        this.perCount = perCount;
    }
}
