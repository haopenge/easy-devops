package com.easy.mapper;

import com.easy.domain.entity.GrayEnvEntity;

/**
* @author liupenghao
* @description 针对表【gray_env(灰度环境)】的数据库操作Mapper
* @createDate 2022-11-01 18:11:34
* @Entity com.easy.domain.entity.GrayEnvEntity
*/
public interface GrayEnvMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(GrayEnvEntity record);

    int insertSelective(GrayEnvEntity record);

    GrayEnvEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GrayEnvEntity record);

    int updateByPrimaryKey(GrayEnvEntity record);

}
