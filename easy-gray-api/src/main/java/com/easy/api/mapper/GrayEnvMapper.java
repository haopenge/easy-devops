package com.easy.api.mapper;

import com.easy.api.domain.entity.GrayEnvEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author liupenghao
* @description 针对表【gray_env(灰度环境)】的数据库操作Mapper
* @createDate 2022-11-01 18:11:34
* @Entity com.easy.domain.entity.GrayEnvEntity
*/
@Repository
public interface GrayEnvMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(GrayEnvEntity record);

    int insertSelective(GrayEnvEntity record);

    GrayEnvEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GrayEnvEntity record);

    int updateByPrimaryKey(GrayEnvEntity record);

    List<GrayEnvEntity> selectByState(Integer state);
}
