package com.xhk.labmanage.service;

import com.xhk.labmanage.common.constant.ProjectConstant;
import com.xhk.labmanage.dao.IndexImgDao;
import com.xhk.labmanage.model.IndexImg;
import com.xhk.labmanage.rmodel.IndexImgGetResponse;
import com.xhk.labmanage.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * create by xhk on 2018/4/8
 */
@Service
public class IndexManageService {
    private Logger logger = LoggerFactory.getLogger(IndexManageService.class);

    @Autowired
    private IndexImgDao indexImgDao;

    public IndexImgGetResponse getIndexImg(){
        List<IndexImg> indexImgList = indexImgDao.getEntityList();
        int size = indexImgList.size();
        for (int i = 0; i < size; i++){
            indexImgList.get(i).setUrl(ProjectConstant.INDEX_IMG_DIR+ File.separator+indexImgList.get(i).getUrl());
        }
        for (int i = 0; i < 3 - size; i++) {
            // 不够三个的凑满
            IndexImg indexImg = new IndexImg();
            indexImg.setSeq(i+1+size);
            indexImgList.add(indexImg);
        }
        IndexImgGetResponse response = new IndexImgGetResponse();
        response.setIndexImgList(indexImgList);
        return response;
    }

    public void imgSaveDB(String filename,int seq){
        IndexImg indexImg = new IndexImg();
        indexImg.setSeq(seq);
        indexImg.setUrl(filename);
        indexImg.setCreateTime(DateUtil.getCurrentTime());
        indexImg.setUpdateTime(DateUtil.getCurrentTime());
        int updateNum = indexImgDao.updateEntity(indexImg);
        if (updateNum == 0){
            // 没有该seq图片
            indexImgDao.insert(indexImg);
        }
    }
}
