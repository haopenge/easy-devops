package com.easy.devops.api.mapper;

import com.easy.devops.api.domain.entity.EasyRepositoryEntityExample;
import com.easy.devops.api.domain.entity.EasyRepositoryEntity;

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

public interface EasyRepositoryEntityMapper {
    @SelectProvider(type=EasyRepositoryEntitySqlProvider.class, method="countByExample")
    int countByExample(EasyRepositoryEntityExample example);

    @DeleteProvider(type=EasyRepositoryEntitySqlProvider.class, method="deleteByExample")
    int deleteByExample(EasyRepositoryEntityExample example);

    @Delete({
        "delete from easy_repository",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into easy_repository (id, create_time, ",
        "update_time, description, ",
        "name, branch, clone_url, ",
        "hash_code)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{description,jdbcType=VARCHAR}, ",
        "#{name,jdbcType=VARCHAR}, #{branch,jdbcType=VARCHAR}, #{cloneUrl,jdbcType=VARCHAR}, ",
        "#{hashCode,jdbcType=VARCHAR})"
    })
    int insert(EasyRepositoryEntity record);

    @InsertProvider(type=EasyRepositoryEntitySqlProvider.class, method="insertSelective")
    int insertSelective(EasyRepositoryEntity record);

    @SelectProvider(type=EasyRepositoryEntitySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="branch", property="branch", jdbcType=JdbcType.VARCHAR),
        @Result(column="clone_url", property="cloneUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="hash_code", property="hashCode", jdbcType=JdbcType.VARCHAR)
    })
    List<EasyRepositoryEntity> selectByExample(EasyRepositoryEntityExample example);

    @Select({
        "select",
        "id, create_time, update_time, description, name, branch, clone_url, hash_code",
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
        @Result(column="hash_code", property="hashCode", jdbcType=JdbcType.VARCHAR)
    })
    EasyRepositoryEntity selectByPrimaryKey(Integer id);

    @UpdateProvider(type=EasyRepositoryEntitySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") EasyRepositoryEntity record, @Param("example") EasyRepositoryEntityExample example);

    @UpdateProvider(type=EasyRepositoryEntitySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") EasyRepositoryEntity record, @Param("example") EasyRepositoryEntityExample example);

    @UpdateProvider(type=EasyRepositoryEntitySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(EasyRepositoryEntity record);

    @Update({
        "update easy_repository",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "description = #{description,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "branch = #{branch,jdbcType=VARCHAR},",
          "clone_url = #{cloneUrl,jdbcType=VARCHAR},",
          "hash_code = #{hashCode,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(EasyRepositoryEntity record);
}
