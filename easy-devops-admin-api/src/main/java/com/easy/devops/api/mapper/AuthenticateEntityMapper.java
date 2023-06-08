package com.easy.devops.api.mapper;

import com.easy.devops.api.domain.entity.AuthenticateEntity;
import com.easy.devops.api.domain.entity.AuthenticateEntityExample;
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

public interface AuthenticateEntityMapper {
    @SelectProvider(type=AuthenticateEntitySqlProvider.class, method="countByExample")
    int countByExample(AuthenticateEntityExample example);

    @DeleteProvider(type=AuthenticateEntitySqlProvider.class, method="deleteByExample")
    int deleteByExample(AuthenticateEntityExample example);

    @Delete({
        "delete from easy_authenticate",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into easy_authenticate (id, create_time, ",
        "update_time, name, ",
        "description, username, ",
        "password, type, ",
        "ssh_private_key_file_name, ssh_private_key)",
        "values (#{id,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{name,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR}, #{username,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{type,jdbcType=INTEGER}, ",
        "#{sshPrivateKeyFileName,jdbcType=VARCHAR}, #{sshPrivateKey,jdbcType=LONGVARCHAR})"
    })
    int insert(AuthenticateEntity record);

    @InsertProvider(type=AuthenticateEntitySqlProvider.class, method="insertSelective")
    int insertSelective(AuthenticateEntity record);

    @SelectProvider(type=AuthenticateEntitySqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="ssh_private_key_file_name", property="sshPrivateKeyFileName", jdbcType=JdbcType.VARCHAR),
        @Result(column="ssh_private_key", property="sshPrivateKey", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<AuthenticateEntity> selectByExampleWithBLOBs(AuthenticateEntityExample example);

    @SelectProvider(type=AuthenticateEntitySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="ssh_private_key_file_name", property="sshPrivateKeyFileName", jdbcType=JdbcType.VARCHAR)
    })
    List<AuthenticateEntity> selectByExample(AuthenticateEntityExample example);

    @Select({
        "select",
        "id, create_time, update_time, name, description, username, password, type, ssh_private_key_file_name, ",
        "ssh_private_key",
        "from easy_authenticate",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="ssh_private_key_file_name", property="sshPrivateKeyFileName", jdbcType=JdbcType.VARCHAR),
        @Result(column="ssh_private_key", property="sshPrivateKey", jdbcType=JdbcType.LONGVARCHAR)
    })
    AuthenticateEntity selectByPrimaryKey(Integer id);

    @UpdateProvider(type=AuthenticateEntitySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") AuthenticateEntity record, @Param("example") AuthenticateEntityExample example);

    @UpdateProvider(type=AuthenticateEntitySqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") AuthenticateEntity record, @Param("example") AuthenticateEntityExample example);

    @UpdateProvider(type=AuthenticateEntitySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") AuthenticateEntity record, @Param("example") AuthenticateEntityExample example);

    @UpdateProvider(type=AuthenticateEntitySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AuthenticateEntity record);

    @Update({
        "update easy_authenticate",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "name = #{name,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "ssh_private_key_file_name = #{sshPrivateKeyFileName,jdbcType=VARCHAR},",
          "ssh_private_key = #{sshPrivateKey,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(AuthenticateEntity record);

    @Update({
        "update easy_authenticate",
        "set create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "name = #{name,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "ssh_private_key_file_name = #{sshPrivateKeyFileName,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AuthenticateEntity record);
}