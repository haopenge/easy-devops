package com.easy.api.mapper;

import com.easy.api.domain.entity.GrayProjectEntity;
import com.easy.api.domain.entity.GrayProjectEntityExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface GrayProjectEntityMapper {
    @SelectProvider(type=GrayProjectEntitySqlProvider.class, method="countByExample")
    int countByExample(GrayProjectEntityExample example);

    @DeleteProvider(type=GrayProjectEntitySqlProvider.class, method="deleteByExample")
    int deleteByExample(GrayProjectEntityExample example);

    @Delete({
        "delete from gray_project",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into gray_project (id, create_time, ",
        "update_time, gray_env_id, ",
        "description, `name`, ",
        "full_name, branch, ",
        "clone_url, `status`, ",
        "git_name)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{grayEnvId,jdbcType=INTEGER}, ",
        "#{description,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{fullName,jdbcType=VARCHAR}, #{branch,jdbcType=VARCHAR}, ",
        "#{cloneUrl,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, ",
        "#{gitName,jdbcType=VARCHAR})"
    })
    int insert(GrayProjectEntity record);

    @InsertProvider(type=GrayProjectEntitySqlProvider.class, method="insertSelective")
    int insertSelective(GrayProjectEntity record);

    @SelectProvider(type=GrayProjectEntitySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="gray_env_id", property="grayEnvId", jdbcType=JdbcType.INTEGER),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="full_name", property="fullName", jdbcType=JdbcType.VARCHAR),
        @Result(column="branch", property="branch", jdbcType=JdbcType.VARCHAR),
        @Result(column="clone_url", property="cloneUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="git_name", property="gitName", jdbcType=JdbcType.VARCHAR)
    })
    List<GrayProjectEntity> selectByExample(GrayProjectEntityExample example);

    @Select({
        "select",
        "id, create_time, update_time, gray_env_id, description, `name`, full_name, branch, ",
        "clone_url, `status`, git_name",
        "from gray_project",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="gray_env_id", property="grayEnvId", jdbcType=JdbcType.INTEGER),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="full_name", property="fullName", jdbcType=JdbcType.VARCHAR),
        @Result(column="branch", property="branch", jdbcType=JdbcType.VARCHAR),
        @Result(column="clone_url", property="cloneUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="git_name", property="gitName", jdbcType=JdbcType.VARCHAR)
    })
    GrayProjectEntity selectByPrimaryKey(Integer id);

    @UpdateProvider(type=GrayProjectEntitySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") GrayProjectEntity record, @Param("example") GrayProjectEntityExample example);

    @UpdateProvider(type=GrayProjectEntitySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") GrayProjectEntity record, @Param("example") GrayProjectEntityExample example);

    @UpdateProvider(type=GrayProjectEntitySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(GrayProjectEntity record);

    @Update({
        "update gray_project",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "gray_env_id = #{grayEnvId,jdbcType=INTEGER},",
          "description = #{description,jdbcType=VARCHAR},",
          "`name` = #{name,jdbcType=VARCHAR},",
          "full_name = #{fullName,jdbcType=VARCHAR},",
          "branch = #{branch,jdbcType=VARCHAR},",
          "clone_url = #{cloneUrl,jdbcType=VARCHAR},",
          "`status` = #{status,jdbcType=INTEGER},",
          "git_name = #{gitName,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(GrayProjectEntity record);
}
