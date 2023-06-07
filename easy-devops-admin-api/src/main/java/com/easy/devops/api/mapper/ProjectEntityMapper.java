package com.easy.devops.api.mapper;

import com.easy.devops.api.domain.entity.ProjectEntity;
import com.easy.devops.api.domain.entity.ProjectEntityExample;
import java.util.List;
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

public interface ProjectEntityMapper {
    @SelectProvider(type=ProjectEntitySqlProvider.class, method="countByExample")
    int countByExample(ProjectEntityExample example);

    @DeleteProvider(type=ProjectEntitySqlProvider.class, method="deleteByExample")
    int deleteByExample(ProjectEntityExample example);

    @Delete({
        "delete from easy_project",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into easy_project (id, create_time, ",
        "update_time, name, ",
        "easy_env_id, easy_repository_id, ",
        "branch)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{name,jdbcType=VARCHAR}, ",
        "#{easyEnvId,jdbcType=INTEGER}, #{easyRepositoryId,jdbcType=INTEGER}, ",
        "#{branch,jdbcType=VARCHAR})"
    })
    int insert(ProjectEntity record);

    @InsertProvider(type=ProjectEntitySqlProvider.class, method="insertSelective")
    int insertSelective(ProjectEntity record);

    @SelectProvider(type=ProjectEntitySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="easy_env_id", property="easyEnvId", jdbcType=JdbcType.INTEGER),
        @Result(column="easy_repository_id", property="easyRepositoryId", jdbcType=JdbcType.INTEGER),
        @Result(column="branch", property="branch", jdbcType=JdbcType.VARCHAR)
    })
    List<ProjectEntity> selectByExample(ProjectEntityExample example);

    @Select({
        "select",
        "id, create_time, update_time, name, easy_env_id, easy_repository_id, branch",
        "from easy_project",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="easy_env_id", property="easyEnvId", jdbcType=JdbcType.INTEGER),
        @Result(column="easy_repository_id", property="easyRepositoryId", jdbcType=JdbcType.INTEGER),
        @Result(column="branch", property="branch", jdbcType=JdbcType.VARCHAR)
    })
    ProjectEntity selectByPrimaryKey(Integer id);

    @UpdateProvider(type=ProjectEntitySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ProjectEntity record, @Param("example") ProjectEntityExample example);

    @UpdateProvider(type=ProjectEntitySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ProjectEntity record, @Param("example") ProjectEntityExample example);

    @UpdateProvider(type=ProjectEntitySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ProjectEntity record);

    @Update({
        "update easy_project",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "name = #{name,jdbcType=VARCHAR},",
          "easy_env_id = #{easyEnvId,jdbcType=INTEGER},",
          "easy_repository_id = #{easyRepositoryId,jdbcType=INTEGER},",
          "branch = #{branch,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ProjectEntity record);
}