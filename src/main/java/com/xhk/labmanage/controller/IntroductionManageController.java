package com.xhk.labmanage.controller;

import com.xhk.labmanage.common.ProjectException;
import com.xhk.labmanage.common.aspect.RetFormat;
import com.xhk.labmanage.common.constant.ErrorCodeMap;
import com.xhk.labmanage.model.Introduction;
import com.xhk.labmanage.rmodel.IntroductionChangeRequest;
import com.xhk.labmanage.rmodel.IntroductionGetResponse;
import com.xhk.labmanage.service.IntroductionManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 实验室介绍管理
 * create by xhk on 2018/4/13
 */
@Controller
public class IntroductionManageController {
    private static Logger logger = LoggerFactory.getLogger(IntroductionManageController.class);

    @Autowired
    private IntroductionManageService introductionManageService;

    @RequestMapping("introduction-manage")
    @RetFormat(isPage = true)
    public String manageIntroduction(ModelMap modelMap){
        IntroductionGetResponse response = introductionManageService.getIntroduction();
        modelMap.addAttribute("introduction",response.getIntroduction());
        return "page/introduction-manage";
    }

    @RequestMapping("introduction-change")
    @ResponseBody
    @RetFormat
    public Object changeIntroduction(Introduction introduction){
        if (introduction == null){
            throw new ProjectException(ErrorCodeMap.PARAMETER_EMPTY_ERROR);
        }
        IntroductionChangeRequest request = new IntroductionChangeRequest();
        request.setIntroduction(introduction);
        introductionManageService.changeIntroduction(request);
        return "";
    }

}
