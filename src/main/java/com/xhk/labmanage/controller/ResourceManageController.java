package com.xhk.labmanage.controller;

import com.xhk.labmanage.common.ProjectException;
import com.xhk.labmanage.common.aspect.RetFormat;
import com.xhk.labmanage.common.constant.ErrorCodeMap;
import com.xhk.labmanage.common.constant.ProjectConstant;
import com.xhk.labmanage.model.Resource;
import com.xhk.labmanage.rmodel.*;
import com.xhk.labmanage.service.FileOperateService;
import com.xhk.labmanage.service.ResourceManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * create by xhk on 2018/4/25
 */
@Controller
public class ResourceManageController {
    private static Logger logger = LoggerFactory.getLogger(ResourceManageController.class);

    @Autowired
    private FileOperateService fileOperateService;

    @Autowired
    private ResourceManageService resourceManageService;

    @RequestMapping("resource-manage")
    @RetFormat(isPage = true)
    public String resourceManage(Integer whichPage,Integer perCount, ModelMap modelMap){

        whichPage = whichPage == null ? 1 : whichPage;
        perCount = perCount == null ? 10 : perCount;
        ResourceGetRequest request = new ResourceGetRequest();
        request.setPerCount(perCount);
        request.setWhichPage(whichPage);
        ResourceGetResponse response = resourceManageService.getResourceList(request);
        modelMap.addAttribute("list",response.getResourceList());
        modelMap.addAttribute("whichPage",whichPage);
        modelMap.addAttribute("perCount",perCount);
        modelMap.addAttribute("allCount",response.getTotalNum());
        return "page/resource-manage";
    }

    @RequestMapping("resource-change-page")
    @RetFormat(isPage = true)
    public String resourceChangePage(Integer resourceId,ModelMap modelMap){
        if (resourceId != null){
            ResourceDetailGetRequest request = new ResourceDetailGetRequest();
            request.setResourceId(resourceId);
            ResourceDetailGetResponse response = resourceManageService.getResourceDetail(request);
            modelMap.addAttribute("resource",response.getResource());
        }
        return "page/resource-change";
    }

    @RequestMapping("resource-change")
    @ResponseBody
    @RetFormat
    public Object resourceChange(@RequestParam(value="file",required=false) MultipartFile file, Integer resourceId, String name, String ename) throws Exception {
        if ((resourceId == null && file == null) || name == null || ename == null){
            throw new ProjectException(ErrorCodeMap.PARAMETER_LACK_ERROR);
        }
        Resource resource = new Resource();
        resource.setId(resourceId);
        resource.setName(name);
        resource.setEname(ename);
        if (file != null){
            String filePath = ProjectConstant.FILE_SAVE_DIR + File.separator;
            String filename = fileOperateService.saveFile(file, filePath);
            resource.setUrl(filename);
        }
        ResourceChangeRequest request = new ResourceChangeRequest();
        request.setResource(resource);
        resourceManageService.changeResource(request);
        return "success";

    }

    @RequestMapping("resource-delete")
    @ResponseBody
    @RetFormat
    public Object deleteResource(Integer resourceId){
        if (resourceId == null){
            throw new ProjectException(ErrorCodeMap.PARAMETER_EMPTY_ERROR);
        }
        ResourceDeleteRequest request = new ResourceDeleteRequest();
        request.setResourceId(resourceId);
        resourceManageService.deleteResource(request);
        return "success";
    }
}
