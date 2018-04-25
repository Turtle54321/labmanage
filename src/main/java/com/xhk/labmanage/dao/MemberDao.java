package com.xhk.labmanage.dao;

import com.xhk.labmanage.model.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * create by xhk on 2018/4/23
 */
public interface MemberDao {

    /**
     * 删除单个成员
     * @param id
     * @return
     */
    @Delete("delete from member where id = #{id};")
    public int delete(@Param(value = "id")Integer id);

    /**
     * 新增成员
     * @param member
     * @return
     */
    @Insert("insert into member (name,ename,head_url,status,note,enote,content,econtent,content_url,enter_time,graduate_time,create_time,update_time) values (#{name},#{ename},#{headUrl},#{status},#{note},#{enote},#{content},#{econtent},#{contentUrl},#{enterTime},#{graduateTime},#{createTime},#{updateTime});")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() ", keyProperty = "id", before = false, resultType = int.class)
    public int addEntity(Member member);

    /**
     * 获取特定成员信息
     * @param id
     * @return
     */
    @Select("select * from member where id = #{id};")
    public Member getEntityById(@Param(value = "id") Integer id);

    /**
     * 获取特定身份在读生，按入学时间递增
     * @param status
     * @return
     */
    @Select("select * from member where status = #{status} and ISNULL(graduate_time) order by enter_time asc;")
    public List<Member> getStudentsByStatus(@Param(value = "status") Integer status);

    /**
     * 获取毕业生，按入学时间递增
     * @param status
     * @return
     */
    @Select("select * from member where status = #{status} and !ISNULL(graduate_time) order by enter_time asc;")
    public List<Member> getGraduatesByStatus(@Param(value = "status") Integer status);

    /**
     * 获取老师
     * @return
     */
    @Select("select * from member where status = 1")
    public List<Member> getTeachers();

    /**
     * 选择性的更新member
     * @param member
     * @return
     */
    public int updateEntity(Member member);

    /**
     * 按创建时间逆序选特定身份的成员
     * @param status
     * @return
     */
    @Select("select * from member where status = #{status} order by create_time desc limit #{start},#{num};")
    public List<Member> getEntityListByPageStatus(@Param(value = "status")Integer status,@Param(value = "start")Integer start,@Param(value = "num")Integer num );

    /**
     * 获取总数
     * @param status
     * @return
     */
    @Select("select count(*) from member where status = #{status};")
    public int countNum(@Param(value = "status")Integer status);
}
