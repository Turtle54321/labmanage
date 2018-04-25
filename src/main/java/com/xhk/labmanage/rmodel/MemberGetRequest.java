package com.xhk.labmanage.rmodel;

/**
 * create by xhk on 2018/4/24
 */
public class MemberGetRequest {
    private Integer whichPage;
    private Integer perCount;
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
