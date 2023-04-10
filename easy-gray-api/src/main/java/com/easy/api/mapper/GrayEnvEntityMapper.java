package com.easy.api.mapper;

import com.easy.api.domain.entity.GrayEnvEntity;
import com.easy.api.domain.entity.GrayEnvEntityExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface GrayEnvEntityMapper {
    @SelectProvider(type=GrayEnvEntitySqlProvider.class, method="countByExample")
    int countByExample(GrayEnvEntityExample example);

    @DeleteProvider(type=GrayEnvEntitySqlProvider.class, method="deleteByExample")
    int deleteByExample(GrayEnvEntityExample example);

    @Delete({
        "delete from gray_env",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into gray_env (id, create_time, ",
        "update_time, `state`, ",
        "description, `name`, ",
        "expire_time, ext_obj)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{state,jdbcType=INTEGER}, ",
        "#{description,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{expireTime,jdbcType=TIMESTAMP}, #{extObj,jdbcType=VARCHAR})"
    })
    int insert(GrayEnvEntity record);

    @InsertProvider(type=GrayEnvEntitySqlProvider.class, method="insertSelective")
    int insertSelective(GrayEnvEntity record);

    @SelectProvider(type=GrayEnvEntitySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="state", property="state", jdbcType=JdbcType.INTEGER),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="expire_time", property="expireTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ext_obj", property="extObj", jdbcType=JdbcType.VARCHAR)
    })
    List<GrayEnvEntity> selectByExample(GrayEnvEntityExample example);

    @Select({
        "select",
        "id, create_time, update_time, `state`, description, `name`, expire_time, ext_obj",
        "from gray_env",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="state", property="state", jdbcType=JdbcType.INTEGER),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="expire_time", property="expireTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="ext_obj", property="extObj", jdbcType=JdbcType.VARCHAR)
    })
    GrayEnvEntity selectByPrimaryKey(Integer id);

    @UpdateProvider(type=GrayEnvEntitySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") GrayEnvEntity record, @Param("example") GrayEnvEntityExample example);

    @UpdateProvider(type=GrayEnvEntitySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") GrayEnvEntity record, @Param("example") GrayEnvEntityExample example);

    @UpdateProvider(type=GrayEnvEntitySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(GrayEnvEntity record);

    @Update({
        "update gray_env",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "`state` = #{state,jdbcType=INTEGER},",
          "description = #{description,jdbcType=VARCHAR},",
          "`name` = #{name,jdbcType=VARCHAR},",
          "expire_time = #{expireTime,jdbcType=TIMESTAMP},",
          "ext_obj = #{extObj,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(GrayEnvEntity record);
}
