package com.xhk.labmanage;

import com.xhk.labmanage.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * create by xhk on 2018/3/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RedisTest {

    @Test
    public void redisTest() {
        RedisUtil.setValue("test","1");
        System.out.println(RedisUtil.getValue("test"));
    }
}
