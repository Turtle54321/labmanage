package com.xhk.labmanage.dao;

import com.xhk.labmanage.model.News;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * create by xhk on 2018/4/14
 */
public interface NewsDao {

    @Select("select * from news limit #{start},#{end};")
    public List<News> getEntityListByPage(int start,int end);
}
