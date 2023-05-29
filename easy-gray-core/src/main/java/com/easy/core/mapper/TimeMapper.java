package com.easy.core.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * @author liuph
 */
@Mapper
public interface TimeMapper {
    @Select("select NOW() where 1 = 1")
    Date getNowTime();
}
