package com.xhk.labmanage.controller;

import com.xhk.labmanage.common.ProjectException;
import com.xhk.labmanage.common.aspect.RetFormat;
import com.xhk.labmanage.common.constant.ErrorCodeMap;
import com.xhk.labmanage.common.constant.ProjectConstant;
import com.xhk.labmanage.model.IndexImg;
import com.xhk.labmanage.rmodel.IndexImgGetResponse;
import com.xhk.labmanage.service.FileOperateService;
import com.xhk.labmanage.service.IndexManageService;
import org.apache.maven.model.Model;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * create by xhk on 2018/4/7
 */
@Controller
public class IndexManageController {
    private static Logger logger = LoggerFactory.getLogger(IndexManageController.class);

    @Autowired
    private IndexManageService indexManageService;

    @Autowired
    private FileOperateService fileOperateService;
    /**
     * 进入首页管理页面，返回前端轮播图片
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "index-manage")
    @RetFormat(isPage = true)
    public String indexManage(ModelMap modelMap){
        String s = toString();
        IndexImgGetResponse response = indexManageService.getIndexImg();
        modelMap.addAttribute("list",response.getIndexImgList());
        return "page/index-manage";
    }

    @RequestMapping(value = "img-upload/{type}")
    @ResponseBody
    @RetFormat
    public Object uploadIndexImg(@RequestParam(value="file",required=false) MultipartFile file,
                                 Integer seq, @PathVariable String type) throws Exception {
        if (file == null){
            throw new ProjectException(ErrorCodeMap.FILE_EMPTY_ERROR);
        }
        if (seq == null){
            throw new ProjectException(ErrorCodeMap.PARAMETER_LACK_ERROR);
        }
        String filePath = ProjectConstant.IMG_SAVE_DIR+File.separator+type+File.separator;
        logger.info(filePath);

        String filename = fileOperateService.saveFile(file, filePath);
        indexManageService.imgSaveDB(filename,seq);

        Map map = new HashMap();
        map.put("img_url",ProjectConstant.INDEX_IMG_DIR+"/"+filename);
        return map;
    }
}
