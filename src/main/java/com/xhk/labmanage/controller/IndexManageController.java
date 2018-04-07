package com.xhk.labmanage.controller;

import com.xhk.labmanage.common.aspect.RetFormat;
import org.apache.maven.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * create by xhk on 2018/4/7
 */
@Controller
public class IndexManageController {
    private static Logger logger = LoggerFactory.getLogger(IndexManageController.class);

    @RequestMapping(value = "index-manage")
    @RetFormat(isPage = true)
    public String indexManage(ModelMap modelMap){

        return "page/index-manage";
    }
}
