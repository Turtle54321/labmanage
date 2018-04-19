package com.xhk.labmanage.controller;

import com.alibaba.fastjson.JSONObject;
import com.xhk.labmanage.common.ProjectException;
import com.xhk.labmanage.common.aspect.RetFormat;
import com.xhk.labmanage.common.constant.ErrorCodeMap;
import com.xhk.labmanage.common.constant.ProjectConstant;
import com.xhk.labmanage.service.FileOperateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by xhk on 2018/4/19
 */
@Controller
public class FileUploadController {
    private static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private FileOperateService fileOperateService;

    @RequestMapping("wang-img-upload/{type}")
    @ResponseBody
    @RetFormat(isPage = true)
    public Object wangImgMultiUpload(@RequestParam(value="files",required=false)MultipartFile[] files, @PathVariable String type) {
        if (files == null){
            throw new ProjectException(ErrorCodeMap.FILE_EMPTY_ERROR);
        }
        String filePath = ProjectConstant.IMG_SAVE_DIR+ File.separator+type+File.separator;
        logger.info(filePath);
        List<String> realPathList = new ArrayList<>();
        int errno = 0;
        for (MultipartFile file : files) {

            String filename = null;
            try {
                filename = fileOperateService.saveFile(file, filePath);
            } catch (Exception e) {
                logger.error("",e);
                errno = 9999;
                break;
            }
            realPathList.add(ProjectConstant.IMG_SAVE_DIR+"/"+type+"/"+filename);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errno",errno);
        jsonObject.put("data",realPathList);
        return jsonObject;
    }
}
