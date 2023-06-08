package com.easy.devops.api.controller;


import com.easy.core.domain.ApiResult;
import com.easy.devops.api.domain.enumx.AdminApiFailureEnum;
import com.easy.devops.api.domain.vo.request.AddRepositoryRequestVo;
import com.easy.devops.api.domain.vo.request.EditRepositoryRequestVo;
import com.easy.devops.api.domain.vo.response.RepositoryResponseVo;
import com.easy.devops.api.exception.AdminApiException;
import com.easy.devops.api.service.impl.RepositoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 仓库
 *
 * @author liupenghao
 */
@RequestMapping("/repository")
@RestController
public class RepositoryController extends BaseController {

    @Resource
    private RepositoryService repositoryService;


    /**
     * 新增仓库信息
     */
    @PostMapping("/add")
    public ApiResult<Integer> add(@RequestBody AddRepositoryRequestVo requestVo) {
        String name = requestVo.getName();
        String cloneUrl = requestVo.getCloneUrl();
        Integer easyAuthenticateId = requestVo.getEasyAuthenticateId();

        if (StringUtils.isAnyBlank(name, cloneUrl) || Objects.isNull(easyAuthenticateId)) {
            throw new AdminApiException(AdminApiFailureEnum.REPOSITORY_NAME_EXISTS);
        }
        Integer id = repositoryService.add(requestVo);
        return success(id);
    }

    /**
     * 修改仓库信息
     */
    @PutMapping("/edit")
    public ApiResult<Void> edit(@RequestBody EditRepositoryRequestVo requestVo) {
        Integer id = requestVo.getId();
        if (Objects.isNull(id)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        repositoryService.edit(requestVo);
        return success();
    }

    /**
     * 删除仓库信息
     */
    @DeleteMapping("/deleteById")
    public ApiResult<Void> deleteById(Integer id) {
        if (Objects.isNull(id)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        repositoryService.deleteById(id);
        return success();
    }


    /**
     * 获取仓库信息
     */
    @GetMapping("/findAll")
    public ApiResult<List<RepositoryResponseVo>> findAll() {
        List<RepositoryResponseVo> data = repositoryService.findAll();
        return success(data);
    }


}
