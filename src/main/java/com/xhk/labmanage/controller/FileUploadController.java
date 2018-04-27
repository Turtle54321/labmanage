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
import org.springframework.ui.ModelMap;
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

    @RequestMapping("file-upload-page")
    @ResponseBody
    @RetFormat(isPage = true)
    public Object fileUploadPage(ModelMap modelMap) {

        return "page/file-upload";
    }

    @RequestMapping(value = "file-upload")
    @ResponseBody
    @RetFormat
    public Object uploadIndexImg(@RequestParam(value="file",required=false) MultipartFile file) throws Exception {
        if (file == null){
            throw new ProjectException(ErrorCodeMap.FILE_EMPTY_ERROR);
        }
        String filePath = ProjectConstant.FILE_SAVE_DIR+File.separator;

        String filename = fileOperateService.saveFile(file, filePath);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("file_url",ProjectConstant.FILE_DIR+"/"+filename);
        return jsonObject;
    }

    @RequestMapping(value = "multi-file-upload")
    @ResponseBody
    @RetFormat
    public Object uploadIndexImg(@RequestParam(value="file",required=false) MultipartFile[] files) throws Exception {
        if (files == null){
            throw new ProjectException(ErrorCodeMap.FILE_EMPTY_ERROR);
        }
        String filePath = ProjectConstant.FILE_SAVE_DIR+File.separator;

        List<String> filenameList = new ArrayList<>(files.length);
        for (MultipartFile file : files){
            String filename = ProjectConstant.FILE_DIR+"/"+ fileOperateService.saveFile(file, filePath);
            filenameList.add(filename);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("files_url",filenameList);
        return jsonObject;
    }


}
