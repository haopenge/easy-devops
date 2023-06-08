package com.easy.devops.api.mapper;

import com.easy.devops.api.domain.entity.CertificateEntity;
import com.easy.devops.api.domain.entity.CertificateEntityExample;
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

public interface CertificateEntityMapper {
    @SelectProvider(type=CertificateEntitySqlProvider.class, method="countByExample")
    int countByExample(CertificateEntityExample example);

    @DeleteProvider(type=CertificateEntitySqlProvider.class, method="deleteByExample")
    int deleteByExample(CertificateEntityExample example);

    @Delete({
        "delete from easy_certificate",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into easy_certificate (id, create_time, ",
        "update_time, name, ",
        "description, username, ",
        "access_token, repository_type)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{name,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR}, #{username,jdbcType=VARCHAR}, ",
        "#{accessToken,jdbcType=VARCHAR}, #{repositoryType,jdbcType=INTEGER})"
    })
    int insert(CertificateEntity record);

    @InsertProvider(type=CertificateEntitySqlProvider.class, method="insertSelective")
    int insertSelective(CertificateEntity record);

    @SelectProvider(type=CertificateEntitySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="access_token", property="accessToken", jdbcType=JdbcType.VARCHAR),
        @Result(column="repository_type", property="repositoryType", jdbcType=JdbcType.INTEGER)
    })
    List<CertificateEntity> selectByExample(CertificateEntityExample example);

    @Select({
        "select",
        "id, create_time, update_time, name, description, username, access_token, repository_type",
        "from easy_certificate",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="access_token", property="accessToken", jdbcType=JdbcType.VARCHAR),
        @Result(column="repository_type", property="repositoryType", jdbcType=JdbcType.INTEGER)
    })
    CertificateEntity selectByPrimaryKey(Integer id);

    @UpdateProvider(type=CertificateEntitySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") CertificateEntity record, @Param("example") CertificateEntityExample example);

    @UpdateProvider(type=CertificateEntitySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") CertificateEntity record, @Param("example") CertificateEntityExample example);

    @UpdateProvider(type=CertificateEntitySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CertificateEntity record);

    @Update({
        "update easy_certificate",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "name = #{name,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "username = #{username,jdbcType=VARCHAR},",
          "access_token = #{accessToken,jdbcType=VARCHAR},",
          "repository_type = #{repositoryType,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(CertificateEntity record);
}