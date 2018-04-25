package com.xhk.labmanage.service;

import com.xhk.labmanage.common.ProjectException;
import com.xhk.labmanage.common.constant.ErrorCodeMap;
import com.xhk.labmanage.dao.MemberDao;
import com.xhk.labmanage.dao.NewsDao;
import com.xhk.labmanage.model.Member;
import com.xhk.labmanage.model.News;
import com.xhk.labmanage.rmodel.*;
import com.xhk.labmanage.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by xhk on 2018/4/15
 */
@Service
public class MemberManageService {
    private static Logger logger = LoggerFactory.getLogger(MemberManageService.class);

    @Autowired
    private MemberDao memberDao;

    public MemberGetResponse getMemberList(MemberGetRequest request){
        int totalNum = memberDao.countNum(request.getStatus());
        int start = (request.getWhichPage()-1) * request.getPerCount();
        List<Member> memberList = memberDao.getEntityListByPageStatus(request.getStatus(),start,request.getPerCount());
        MemberGetResponse response = new MemberGetResponse();
        response.setMemberList(memberList);
        response.setTotalNum(totalNum);
        return  response;
    }


    public void deteleMember(MemberDeleteRequest request){
        memberDao.delete(request.getMemberId());
    }

    public void changeMember(MemberChangeRequest request){
        Member member  = request.getMember();
        member.setUpdateTime(DateUtil.getCurrentTime());

        if (member.getId() == null){
            // 新增
            member.setCreateTime(DateUtil.getCurrentTime());
            int num = memberDao.addEntity(member);
            if (num == 0){
                throw new ProjectException(ErrorCodeMap.FAIL_TO_ADD);
            }
        }
        else{
            int num = memberDao.updateEntity(member);
            if (num == 0){
                throw new ProjectException(ErrorCodeMap.FAIL_TO_UPDATE);
            }
        }

    }

    public MemberDetailGetResponse getMemberDetail(MemberDetailGetRequest request){
        Member member = memberDao.getEntityById(request.getMemberId());
        MemberDetailGetResponse response = new MemberDetailGetResponse();
        response.setMember(member);
        return response;
    }

}
