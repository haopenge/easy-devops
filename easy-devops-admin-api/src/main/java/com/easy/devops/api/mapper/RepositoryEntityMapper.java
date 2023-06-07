package com.easy.devops.api.mapper;

import com.easy.devops.api.domain.entity.RepositoryEntity;
import com.easy.devops.api.domain.entity.RepositoryEntityExample;
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

public interface RepositoryEntityMapper {
    @SelectProvider(type=RepositoryEntitySqlProvider.class, method="countByExample")
    int countByExample(RepositoryEntityExample example);

    @DeleteProvider(type=RepositoryEntitySqlProvider.class, method="deleteByExample")
    int deleteByExample(RepositoryEntityExample example);

    @Delete({
        "delete from easy_repository",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into easy_repository (id, create_time, ",
        "update_time, description, ",
        "name, branch, clone_url, ",
        "hash_code, easy_authenticate_id)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{description,jdbcType=VARCHAR}, ",
        "#{name,jdbcType=VARCHAR}, #{branch,jdbcType=VARCHAR}, #{cloneUrl,jdbcType=VARCHAR}, ",
        "#{hashCode,jdbcType=VARCHAR}, #{easyAuthenticateId,jdbcType=INTEGER})"
    })
    int insert(RepositoryEntity record);

    @InsertProvider(type=RepositoryEntitySqlProvider.class, method="insertSelective")
    int insertSelective(RepositoryEntity record);

    @SelectProvider(type=RepositoryEntitySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="branch", property="branch", jdbcType=JdbcType.VARCHAR),
        @Result(column="clone_url", property="cloneUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="hash_code", property="hashCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="easy_authenticate_id", property="easyAuthenticateId", jdbcType=JdbcType.INTEGER)
    })
    List<RepositoryEntity> selectByExample(RepositoryEntityExample example);

    @Select({
        "select",
        "id, create_time, update_time, description, name, branch, clone_url, hash_code, ",
        "easy_authenticate_id",
        "from easy_repository",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="branch", property="branch", jdbcType=JdbcType.VARCHAR),
        @Result(column="clone_url", property="cloneUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="hash_code", property="hashCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="easy_authenticate_id", property="easyAuthenticateId", jdbcType=JdbcType.INTEGER)
    })
    RepositoryEntity selectByPrimaryKey(Integer id);

    @UpdateProvider(type=RepositoryEntitySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") RepositoryEntity record, @Param("example") RepositoryEntityExample example);

    @UpdateProvider(type=RepositoryEntitySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") RepositoryEntity record, @Param("example") RepositoryEntityExample example);

    @UpdateProvider(type=RepositoryEntitySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(RepositoryEntity record);

    @Update({
        "update easy_repository",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "description = #{description,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "branch = #{branch,jdbcType=VARCHAR},",
          "clone_url = #{cloneUrl,jdbcType=VARCHAR},",
          "hash_code = #{hashCode,jdbcType=VARCHAR},",
          "easy_authenticate_id = #{easyAuthenticateId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(RepositoryEntity record);
}