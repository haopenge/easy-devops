package com.easy.devops.api.mapper;

import com.easy.devops.api.domain.entity.EasyProjectEntity;
import com.easy.devops.api.domain.entity.EasyProjectEntityExample;
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

public interface EasyProjectEntityMapper {
    @SelectProvider(type=EasyProjectEntitySqlProvider.class, method="countByExample")
    int countByExample(EasyProjectEntityExample example);

    @DeleteProvider(type=EasyProjectEntitySqlProvider.class, method="deleteByExample")
    int deleteByExample(EasyProjectEntityExample example);

    @Delete({
        "delete from easy_project",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into easy_project (id, create_time, ",
        "update_time, easy_env_id, ",
        "easy_repository_id, name, ",
        "build_path, branch, ",
        "hash_code)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{easyEnvId,jdbcType=INTEGER}, ",
        "#{easyRepositoryId,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{buildPath,jdbcType=VARCHAR}, #{branch,jdbcType=VARCHAR}, ",
        "#{hashCode,jdbcType=VARCHAR})"
    })
    int insert(EasyProjectEntity record);

    @InsertProvider(type=EasyProjectEntitySqlProvider.class, method="insertSelective")
    int insertSelective(EasyProjectEntity record);

    @SelectProvider(type=EasyProjectEntitySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="easy_env_id", property="easyEnvId", jdbcType=JdbcType.INTEGER),
        @Result(column="easy_repository_id", property="easyRepositoryId", jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="build_path", property="buildPath", jdbcType=JdbcType.VARCHAR),
        @Result(column="branch", property="branch", jdbcType=JdbcType.VARCHAR),
        @Result(column="hash_code", property="hashCode", jdbcType=JdbcType.VARCHAR)
    })
    List<EasyProjectEntity> selectByExample(EasyProjectEntityExample example);

    @Select({
        "select",
        "id, create_time, update_time, easy_env_id, easy_repository_id, name, build_path, ",
        "branch, hash_code",
        "from easy_project",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="easy_env_id", property="easyEnvId", jdbcType=JdbcType.INTEGER),
        @Result(column="easy_repository_id", property="easyRepositoryId", jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="build_path", property="buildPath", jdbcType=JdbcType.VARCHAR),
        @Result(column="branch", property="branch", jdbcType=JdbcType.VARCHAR),
        @Result(column="hash_code", property="hashCode", jdbcType=JdbcType.VARCHAR)
    })
    EasyProjectEntity selectByPrimaryKey(Integer id);

    @UpdateProvider(type=EasyProjectEntitySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") EasyProjectEntity record, @Param("example") EasyProjectEntityExample example);

    @UpdateProvider(type=EasyProjectEntitySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") EasyProjectEntity record, @Param("example") EasyProjectEntityExample example);

    @UpdateProvider(type=EasyProjectEntitySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(EasyProjectEntity record);

    @Update({
        "update easy_project",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "easy_env_id = #{easyEnvId,jdbcType=INTEGER},",
          "easy_repository_id = #{easyRepositoryId,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "build_path = #{buildPath,jdbcType=VARCHAR},",
          "branch = #{branch,jdbcType=VARCHAR},",
          "hash_code = #{hashCode,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(EasyProjectEntity record);
}
