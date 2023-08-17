package com.youlai.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.system.model.entity.EasyProject;
import org.apache.ibatis.annotations.Mapper;

/**
* @author liupenghao
* @description 针对表【easy_project(项目)】的数据库操作Mapper
* @createDate 2023-08-17 11:42:09
* @Entity com.youlai.system.model.entity.EasyProject
*/
@Mapper
public interface EasyProjectMapper extends BaseMapper<EasyProject> {

}




