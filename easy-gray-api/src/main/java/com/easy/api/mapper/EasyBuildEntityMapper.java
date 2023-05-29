package com.easy.api.mapper;

import com.easy.api.domain.entity.EasyBuildEntity;
import com.easy.api.domain.entity.EasyBuildEntityExample;
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

public interface EasyBuildEntityMapper {
    @SelectProvider(type=EasyBuildEntitySqlProvider.class, method="countByExample")
    int countByExample(EasyBuildEntityExample example);

    @DeleteProvider(type=EasyBuildEntitySqlProvider.class, method="deleteByExample")
    int deleteByExample(EasyBuildEntityExample example);

    @Delete({
        "delete from easy_build",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into easy_build (id, create_time, ",
        "update_time, easy_repository_id, ",
        "order, type, content)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{easyRepositoryId,jdbcType=INTEGER}, ",
        "#{order,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, #{content,jdbcType=LONGVARCHAR})"
    })
    int insert(EasyBuildEntity record);

    @InsertProvider(type=EasyBuildEntitySqlProvider.class, method="insertSelective")
    int insertSelective(EasyBuildEntity record);

    @SelectProvider(type=EasyBuildEntitySqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="easy_repository_id", property="easyRepositoryId", jdbcType=JdbcType.INTEGER),
        @Result(column="order", property="order", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<EasyBuildEntity> selectByExampleWithBLOBs(EasyBuildEntityExample example);

    @SelectProvider(type=EasyBuildEntitySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="easy_repository_id", property="easyRepositoryId", jdbcType=JdbcType.INTEGER),
        @Result(column="order", property="order", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER)
    })
    List<EasyBuildEntity> selectByExample(EasyBuildEntityExample example);

    @Select({
        "select",
        "id, create_time, update_time, easy_repository_id, order, type, content",
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
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    EasyBuildEntity selectByPrimaryKey(Integer id);

    @UpdateProvider(type=EasyBuildEntitySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") EasyBuildEntity record, @Param("example") EasyBuildEntityExample example);

    @UpdateProvider(type=EasyBuildEntitySqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") EasyBuildEntity record, @Param("example") EasyBuildEntityExample example);

    @UpdateProvider(type=EasyBuildEntitySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") EasyBuildEntity record, @Param("example") EasyBuildEntityExample example);

    @UpdateProvider(type=EasyBuildEntitySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(EasyBuildEntity record);

    @Update({
        "update easy_build",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "easy_repository_id = #{easyRepositoryId,jdbcType=INTEGER},",
          "order = #{order,jdbcType=INTEGER},",
          "type = #{type,jdbcType=INTEGER},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(EasyBuildEntity record);

    @Update({
        "update easy_build",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "easy_repository_id = #{easyRepositoryId,jdbcType=INTEGER},",
          "order = #{order,jdbcType=INTEGER},",
          "type = #{type,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(EasyBuildEntity record);
}