package com.easy.devops.api.mapper;

import com.easy.devops.api.domain.entity.BuildEntity;
import com.easy.devops.api.domain.entity.BuildEntityExample;
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

public interface BuildEntityMapper {
    @SelectProvider(type=BuildEntitySqlProvider.class, method="countByExample")
    int countByExample(BuildEntityExample example);

    @DeleteProvider(type=BuildEntitySqlProvider.class, method="deleteByExample")
    int deleteByExample(BuildEntityExample example);

    @Delete({
        "delete from easy_build",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into easy_build (id, create_time, ",
        "update_time, easy_repository_id, ",
        "order, type, hash_code, ",
        "content)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{easyRepositoryId,jdbcType=INTEGER}, ",
        "#{order,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, #{hashCode,jdbcType=VARCHAR}, ",
        "#{content,jdbcType=LONGVARCHAR})"
    })
    int insert(BuildEntity record);

    @InsertProvider(type=BuildEntitySqlProvider.class, method="insertSelective")
    int insertSelective(BuildEntity record);

    @SelectProvider(type=BuildEntitySqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="easy_repository_id", property="easyRepositoryId", jdbcType=JdbcType.INTEGER),
        @Result(column="order", property="order", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="hash_code", property="hashCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<BuildEntity> selectByExampleWithBLOBs(BuildEntityExample example);

    @SelectProvider(type=BuildEntitySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="easy_repository_id", property="easyRepositoryId", jdbcType=JdbcType.INTEGER),
        @Result(column="order", property="order", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="hash_code", property="hashCode", jdbcType=JdbcType.VARCHAR)
    })
    List<BuildEntity> selectByExample(BuildEntityExample example);

    @Select({
        "select",
        "id, create_time, update_time, easy_repository_id, order, type, hash_code, content",
        "from easy_build",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="easy_repository_id", property="easyRepositoryId", jdbcType=JdbcType.INTEGER),
        @Result(column="order", property="order", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="hash_code", property="hashCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    BuildEntity selectByPrimaryKey(Integer id);

    @UpdateProvider(type=BuildEntitySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") BuildEntity record, @Param("example") BuildEntityExample example);

    @UpdateProvider(type=BuildEntitySqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") BuildEntity record, @Param("example") BuildEntityExample example);

    @UpdateProvider(type=BuildEntitySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") BuildEntity record, @Param("example") BuildEntityExample example);

    @UpdateProvider(type=BuildEntitySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BuildEntity record);

    @Update({
        "update easy_build",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "easy_repository_id = #{easyRepositoryId,jdbcType=INTEGER},",
          "order = #{order,jdbcType=INTEGER},",
          "type = #{type,jdbcType=INTEGER},",
          "hash_code = #{hashCode,jdbcType=VARCHAR},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(BuildEntity record);

    @Update({
        "update easy_build",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "easy_repository_id = #{easyRepositoryId,jdbcType=INTEGER},",
          "order = #{order,jdbcType=INTEGER},",
          "type = #{type,jdbcType=INTEGER},",
          "hash_code = #{hashCode,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(BuildEntity record);
}