package com.xhk.labmanage.dao;

import com.xhk.labmanage.model.IndexImg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

/**
 * create by xhk on 2018/4/8
 */
public interface IndexImgDao {

    @Insert("insert into index_img (url, seq, create_time, update_time) values (#{url},#{seq},#{createTime},#{updateTime});")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() ", keyProperty = "id", before = false, resultType = int.class)
    public int insert(IndexImg indexImg);
}
