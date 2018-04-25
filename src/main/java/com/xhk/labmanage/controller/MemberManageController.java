package com.xhk.labmanage.controller;

import com.xhk.labmanage.common.ProjectException;
import com.xhk.labmanage.common.aspect.RetFormat;
import com.xhk.labmanage.common.constant.ErrorCodeMap;
import com.xhk.labmanage.model.Member;
import com.xhk.labmanage.rmodel.*;
import com.xhk.labmanage.service.MemberManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * create by xhk on 2018/4/20
 */
@Controller
public class MemberManageController {
    private static Logger logger = LoggerFactory.getLogger(MemberManageController.class);

    @Autowired
    private MemberManageService memberManageService;

    @RequestMapping("member-manage")
    @RetFormat(isPage = true)
    public String memberManage(Integer whichPage, Integer perCount,Integer status, ModelMap modelMap){
        status = status == null ? 1 : status;
        whichPage = whichPage == null ? 1 : whichPage;
        perCount = perCount == null ? 10 : perCount;
        MemberGetRequest request = new MemberGetRequest();
        request.setPerCount(perCount);
        request.setWhichPage(whichPage);
        request.setStatus(status);
        MemberGetResponse response = memberManageService.getMemberList(request);
        modelMap.addAttribute("list",response.getMemberList());
        modelMap.addAttribute("status",status);
        modelMap.addAttribute("whichPage",whichPage);
        modelMap.addAttribute("perCount",perCount);
        modelMap.addAttribute("allCount",response.getTotalNum());
        return "page/member-manage";
    }

    @RequestMapping("member-change-page")
    @RetFormat(isPage = true)
    public String memberChangePage(Integer memberId, ModelMap modelMap){
        if (memberId != null){
            MemberDetailGetRequest request = new MemberDetailGetRequest();
            request.setMemberId(memberId);
            MemberDetailGetResponse response = memberManageService.getMemberDetail(request);
            modelMap.addAttribute("member",response.getMember());
        }
         return "page/member-change";
    }

    @RequestMapping("member-change")
    @ResponseBody
    @RetFormat
    public Object merberChange(Member member){
        if (member == null){
            throw new ProjectException(ErrorCodeMap.PARAMETER_EMPTY_ERROR);
        }
        MemberChangeRequest request = new MemberChangeRequest();
        request.setMember(member);
        memberManageService.changeMember(request);
        return "success";
    }

    @RequestMapping("member-delete")
    @ResponseBody
    @RetFormat
     public Object deleteMember(Integer memberId){
        if (memberId == null){
            throw new ProjectException(ErrorCodeMap.PARAMETER_EMPTY_ERROR);
        }
        MemberDeleteRequest request = new MemberDeleteRequest();
        request.setMemberId(memberId);
        memberManageService.deteleMember(request);
        return "success";
    }


}
