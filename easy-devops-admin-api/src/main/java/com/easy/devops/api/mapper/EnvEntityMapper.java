package com.easy.devops.api.mapper;

import com.easy.devops.api.domain.entity.EnvEntity;
import com.easy.devops.api.domain.entity.EnvEntityExample;
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

public interface EnvEntityMapper {
    @SelectProvider(type=EnvEntitySqlProvider.class, method="countByExample")
    int countByExample(EnvEntityExample example);

    @DeleteProvider(type=EnvEntitySqlProvider.class, method="deleteByExample")
    int deleteByExample(EnvEntityExample example);

    @Delete({
        "delete from easy_env",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into easy_env (id, create_time, ",
        "update_time, description, ",
        "name, expire_time)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{description,jdbcType=VARCHAR}, ",
        "#{name,jdbcType=VARCHAR}, #{expireTime,jdbcType=TIMESTAMP})"
    })
    int insert(EnvEntity record);

    @InsertProvider(type=EnvEntitySqlProvider.class, method="insertSelective")
    int insertSelective(EnvEntity record);

    @SelectProvider(type=EnvEntitySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="expire_time", property="expireTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<EnvEntity> selectByExample(EnvEntityExample example);

    @Select({
        "select",
        "id, create_time, update_time, description, name, expire_time",
        "from easy_env",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="expire_time", property="expireTime", jdbcType=JdbcType.TIMESTAMP)
    })
    EnvEntity selectByPrimaryKey(Integer id);

    @UpdateProvider(type=EnvEntitySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") EnvEntity record, @Param("example") EnvEntityExample example);

    @UpdateProvider(type=EnvEntitySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") EnvEntity record, @Param("example") EnvEntityExample example);

    @UpdateProvider(type=EnvEntitySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(EnvEntity record);

    @Update({
        "update easy_env",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "description = #{description,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "expire_time = #{expireTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(EnvEntity record);
}