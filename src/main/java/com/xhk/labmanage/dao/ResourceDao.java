package com.xhk.labmanage.dao;

import com.xhk.labmanage.model.Resource;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * create by xhk on 2018/4/26
 */
public interface ResourceDao {

    /**
     * 插入资源
     * @param resource
     * @return
     */
    @Insert("insert into resource (name,ename,url,type,create_time,update_time) values (#{name},#{ename},#{url},#{type},#{createTime},#{updateTime});")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() ", keyProperty = "id", before = false, resultType = int.class)
    public int insert(Resource resource);

    /**
     * 更新非空
     * @param resource
     * @return
     */
    public int updateEntity(Resource resource);

    /**
     * 分页获取时间逆序
     * @param start
     * @param num
     * @return
     */
    @Select("select * from resource order by create_time desc limit #{start},#{num};")
    public List<Resource> getEntityListByPage(@Param(value = "start")Integer start, @Param(value = "num")Integer num);

    /**
     * 获取总数
     * @return
     */
    @Select("select count(*) from resource;")
    public int countNum();

    /**
     * 获取特定id的资源
     * @param id
     * @return
     */
    @Select("select * from resource where id = #{id};")
    public Resource getEntityById(@Param(value = "id")Integer id);

    /**
     * 删除特定id的资源
     * @param id
     * @return
     */
    @Delete("delete from resource where id = #{id}; ")
    public int delete(@Param(value = "id")Integer id);
}
