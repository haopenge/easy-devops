package com.easy.devops.api.mapper;

import com.easy.devops.api.domain.entity.TemplateEntity;
import com.easy.devops.api.domain.entity.TemplateEntityExample;
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

public interface TemplateEntityMapper {
    @SelectProvider(type=TemplateEntitySqlProvider.class, method="countByExample")
    int countByExample(TemplateEntityExample example);

    @DeleteProvider(type=TemplateEntitySqlProvider.class, method="deleteByExample")
    int deleteByExample(TemplateEntityExample example);

    @Delete({
        "delete from easy_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into easy_template (id, create_time, ",
        "update_time, name, ",
        "content_file_key, type)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{name,jdbcType=VARCHAR}, ",
        "#{contentFileKey,jdbcType=VARCHAR}, #{type,jdbcType=INTEGER})"
    })
    int insert(TemplateEntity record);

    @InsertProvider(type=TemplateEntitySqlProvider.class, method="insertSelective")
    int insertSelective(TemplateEntity record);

    @SelectProvider(type=TemplateEntitySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="content_file_key", property="contentFileKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER)
    })
    List<TemplateEntity> selectByExample(TemplateEntityExample example);

    @Select({
        "select",
        "id, create_time, update_time, name, content_file_key, type",
        "from easy_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="content_file_key", property="contentFileKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER)
    })
    TemplateEntity selectByPrimaryKey(Integer id);

    @UpdateProvider(type=TemplateEntitySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TemplateEntity record, @Param("example") TemplateEntityExample example);

    @UpdateProvider(type=TemplateEntitySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TemplateEntity record, @Param("example") TemplateEntityExample example);

    @UpdateProvider(type=TemplateEntitySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TemplateEntity record);

    @Update({
        "update easy_template",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "name = #{name,jdbcType=VARCHAR},",
          "content_file_key = #{contentFileKey,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TemplateEntity record);
}