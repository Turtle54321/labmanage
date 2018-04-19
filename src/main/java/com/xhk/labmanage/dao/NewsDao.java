package com.xhk.labmanage.dao;

import com.xhk.labmanage.model.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * create by xhk on 2018/4/14
 */
public interface NewsDao {

    /**
     * 分页取
     * @param start 取的第一个元素
     * @param num 取的数量
     * @return
     */
    @Select("select * from news limit #{start},#{num};")
    public List<News> getEntityListByPage(@Param(value = "start") int start, @Param(value = "num")int num);

    /**
     * 取总页数
     * @return
     */
    @Select("select count(*) from news;")
    public int countNum();

    /**
     * 插入新闻
     * @param news
     * @return
     */
    @Insert("insert into news (title,etitle,content,econtent,url,create_time,update_time) values (#{title},#{etitle},#{content},#{econtent},#{url},#{createTime},#{updateTime});")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() ", keyProperty = "id", before = false, resultType = int.class)
    public int insert(News news);

    /**
     * 对传入news非空的部分进行更新
     * @param news
     * @return
     */
    public int updateEntity(News news);

    /**
     * 删除新闻
     * @param id
     * @return
     */
    @Delete("delete from news where id = #{id};")
    public int delete(@Param(value = "id")Integer id);

    /**
     * 获取特定新闻
     * @param id
     * @return
     */
    @Select("select * from news where id = #{id};")
    public News getEntityById(@Param(value = "id") Integer id);
}
