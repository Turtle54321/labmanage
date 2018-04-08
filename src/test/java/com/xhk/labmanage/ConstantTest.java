package com.xhk.labmanage;

import com.xhk.labmanage.common.constant.ProjectConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * create by xhk on 2018/4/8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ConstantTest {

    @Test
    public void staticValueTest(){
        System.out.println(ProjectConstant.INDEX_IMG_DIR+"!!!!!!!!!!!!!!!!");
    }
}
