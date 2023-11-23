package com.youlai.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.system.common.enums.AdminApiFailureEnum;
import com.youlai.system.common.exception.AdminApiException;
import com.youlai.system.mapper.EasyEnvMapper;
import com.youlai.system.model.entity.EasyEnv;
import com.youlai.system.model.vo.request.AddEnvRequestVo;
import com.youlai.system.model.vo.request.EditEnvRequestVo;
import com.youlai.system.model.vo.response.EnvResponseVo;
import com.youlai.system.service.EasyEnvService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author liupenghao
 * @description 针对表【easy_env(环境)】的数据库操作Service实现
 * @createDate 2023-11-23 10:37:26
 */
@Service
public class EasyEnvServiceImpl extends ServiceImpl<EasyEnvMapper, EasyEnv>
        implements EasyEnvService {

    public Integer add(AddEnvRequestVo requestVo) {
        EasyEnv addEnv = new EasyEnv();
        addEnv.setDescription(requestVo.getDescription());
        addEnv.setName(requestVo.getName());
        addEnv.setExpireTime(requestVo.getExpireTime());
        baseMapper.insert(addEnv);

        return addEnv.getId();
    }

    public void edit(EditEnvRequestVo requestVo) {
        EasyEnv easyEnv = baseMapper.selectById(requestVo.getId());
        if (Objects.isNull(easyEnv)) {
            throw new AdminApiException(AdminApiFailureEnum.ENV_NOT_EXIST);
        }
        EasyEnv updateEnv = new EasyEnv();
        updateEnv.setDescription(requestVo.getDescription());
        updateEnv.setName(requestVo.getName());
        updateEnv.setExpireTime(requestVo.getExpireTime());
        updateEnv.setId(requestVo.getId());

        baseMapper.updateById(updateEnv);
    }

    public List<EnvResponseVo> findAll() {
        List<EasyEnv> easyEnvList = baseMapper.selectList(new LambdaQueryWrapper<>());
        if(CollectionUtils.isEmpty(easyEnvList)){
            return Collections.emptyList();
        }
        return BeanUtil.copyToList(easyEnvList, EnvResponseVo.class);
    }

    public void deleteById(List<Integer> idList) {
        baseMapper.deleteBatchIds(idList);
    }
}




