package com.xhk.labmanage.dao;

import com.xhk.labmanage.model.IndexImg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * create by xhk on 2018/4/8
 */
public interface IndexImgDao {

    /**
     * 插入轮播图片
     * @param indexImg
     * @return
     */
    @Insert("insert into index_img (url, seq, create_time, update_time) values (#{url},#{seq},#{createTime},#{updateTime});")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() ", keyProperty = "id", before = false, resultType = int.class)
    public int insert(IndexImg indexImg);

    /**
     * 按seq升序获取轮播图片
     * @return
     */
    @Select("select * from index_img order by seq asc limit 3;")
    public List<IndexImg> getEntityList();

    @Update("update index_img set url=#{url}, update_time=#{updateTime} where seq=#{seq};")
    public Integer updateEntity(IndexImg indexImg);
}
