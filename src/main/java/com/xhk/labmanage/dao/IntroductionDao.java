package com.xhk.labmanage.dao;

import com.xhk.labmanage.model.Introduction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

/**
 * create by xhk on 2018/4/13
 */
public interface IntroductionDao {

    /**
     * 获取实验室简介
     * @return
     */
    @Select("select * from introduction order by id asc limit 1")
    public Introduction getEntity();

    /**
     * 插入实验室简介
     * @param introduction
     * @return
     */
    @Insert("insert into introduction (content,econtent,create_time,update_time)values(#{content},#{econtent},#{createTime},#{updateTime});")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() ", keyProperty = "id", before = false, resultType = int.class)
    public int insert(Introduction introduction);

    /**
     * 更新
     * @param introduction
     * @return
     */
    @Update("update introduction set content=#{content}, econtent=#{econtent},update_time=#{updateTime} where id=#{id};")
    public int updateEntity(Introduction introduction);
}
