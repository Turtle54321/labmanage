package com.xhk.labmanage.controller;

import com.xhk.labmanage.common.ProjectException;
import com.xhk.labmanage.common.aspect.RetFormat;
import com.xhk.labmanage.common.constant.ErrorCodeMap;
import com.xhk.labmanage.rmodel.CheckLoginRequest;
import com.xhk.labmanage.service.BackUserService;
import com.xhk.labmanage.utils.HttpUtil;
import com.xhk.labmanage.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台用户controller
 * create by xhk on 2018/3/28
 */
@Controller
public class BackUserController {
    private static Logger logger = LoggerFactory.getLogger(BackUserController.class);

    @Autowired
    private BackUserService backUserService;

    /**
     * 进入到登录界面
     * @return
     */
    @RequestMapping(value = "login")
    @RetFormat(isPage = true)
    public String login(){

        return "page/admin-login";
    }

    /**
     * 管理员登录
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "checkLogin",method = RequestMethod.POST)
    @ResponseBody
    @RetFormat
    public Object checkLogin(CheckLoginRequest request, HttpSession session){
        if (request.getPassword() == null || request.getPassword() == null){
            throw new ProjectException(ErrorCodeMap.PARAMETER_EMPTY_ERROR);
        }
        //判断用户是否存在，将用户登录信息保存在redis中，设置时间为30分钟（1800秒）
        backUserService.checkLogin(request);
        String sessionId = HttpUtil.getHttpRequest().getSession().getId();
        logger.info("登录sessionId: "+sessionId);

        RedisUtil.setValue(sessionId,request.getUsername(),1800);
        session.setAttribute("username",request.getUsername());
        Map ret = new HashMap<>();
        ret.put("status",1);
        ret.put("redirectURL","index-manage.htm");
        return ret;
    }

}
