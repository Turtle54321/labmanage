package com.xhk.labmanage.rmodel;

import com.xhk.labmanage.model.Member;

import java.util.List;

/**
 * create by xhk on 2018/4/24
 */
public class MemberGetResponse {
    private List<Member> memberList;
    private int totalNum;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }
}
