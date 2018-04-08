package com.xhk.labmanage;

import com.xhk.labmanage.dao.IndexImgDao;
import com.xhk.labmanage.model.IndexImg;
import com.xhk.labmanage.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        IndexImg indexImg = new IndexImg();
        indexImg.setUrl("top3.jpg");
        indexImg.setSeq(3);
        indexImg.setCreateTime(DateUtil.getCurrentTime());
        indexImg.setUpdateTime(DateUtil.getCurrentTime());
        System.out.println(indexImgDao.insert(indexImg));
        System.out.println(indexImg.getId());
    }
}
