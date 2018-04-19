package com.xhk.labmanage;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.tools.json.JSONUtil;
import com.xhk.labmanage.dao.IndexImgDao;
import com.xhk.labmanage.dao.NewsDao;
import com.xhk.labmanage.model.IndexImg;
import com.xhk.labmanage.utils.DateUtil;
import com.xhk.labmanage.utils.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * create by xhk on 2018/4/8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DaoTest {

    @Autowired
    private IndexImgDao indexImgDao;

    @Test
    public void indexImgDaoTest(){
//        IndexImg indexImg = new IndexImg();
//        indexImg.setUrl("top3.jpg");
//        indexImg.setSeq(2);
//        indexImg.setCreateTime(DateUtil.getCurrentTime());
//        indexImg.setUpdateTime(DateUtil.getCurrentTime());
//        System.out.println(indexImgDao.updateEntity(indexImg));
//        System.out.println(indexImg.getId());
        System.out.println(JsonUtil.getJsonFromObject(indexImgDao.getEntityList()));
    }

    @Autowired
    private NewsDao newsDao;

    @Test
    public void newsDaoTest(){
        System.out.println(Integer.MAX_VALUE);
        System.out.println(new Date(2147483647000L));
//        System.out.println(JsonUtil.getJsonFromObject(newsDao.getEntityListByPage(0,1)));
    }

}
