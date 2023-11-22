package com.youlai.system.controller;

import com.easy.core.controller.BaseController;
import com.easy.core.domain.ApiResult;
import com.youlai.system.common.enums.AdminApiFailureEnum;
import com.youlai.system.common.exception.AdminApiException;
import com.youlai.system.model.vo.request.AddProjectRequestVo;
import com.youlai.system.model.vo.request.EditProjectRequestVo;
import com.youlai.system.model.vo.request.UpdateConfigVo;
import com.youlai.system.model.vo.response.ProjectConfigResponseVo;
import com.youlai.system.model.vo.response.ProjectResponseVo;
import com.youlai.system.service.impl.EasyProjectServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liupenghao
 */
@RequestMapping("/project")
@RestController
public class ProjectController extends BaseController {

    @Resource
    private EasyProjectServiceImpl projectService;

    /**
     * 获取仓库对应的分支列表
     *
     * @param easyRepositoryId 仓库id
     * @return String
     */
    @GetMapping("/findBranches")
    public ApiResult<List<String>> findBranches(@RequestParam Integer easyRepositoryId) {
        List<String> dataList = projectService.findBranches(easyRepositoryId);
        return success(dataList);
    }

    /**
     * 删除灰度环境的项目
     */
    @DeleteMapping("/deleteById")
    public ApiResult<Void> deleteById(@RequestParam String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        List<Integer> idList = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        projectService.deleteById(idList);
        return success();
    }

    /**
     * 添加项目
     */
    @PostMapping("/add")
    public ApiResult<Void> addProject(@RequestBody AddProjectRequestVo addProjectToGrayEnv) {
        projectService.addProject(addProjectToGrayEnv);
        return success();
    }

    /**
     * 获取项目列表
     */
    @GetMapping("/findAll")
    public ApiResult<List<ProjectResponseVo>> findAll(@RequestParam(required = false, defaultValue = "0") Integer envId) {
        List<ProjectResponseVo> dataList = projectService.findAll(envId);
        return success(dataList);
    }

    /**
     * 编辑项目
     */
    @PutMapping("/edit")
    public ApiResult<Void> editProject(@RequestBody EditProjectRequestVo editProjectRequestVo) {
        projectService.editProject(editProjectRequestVo);
        return success();
    }

    /**
     * 配置项目
     *
     * @param configVo 项目Vo
     * @return Void
     */
    @PostMapping("/config")
    public ApiResult<Void> configProject(@RequestBody UpdateConfigVo configVo) {
        if (StringUtils.isAnyBlank(configVo.getDockerScript(), configVo.getPushScript()) || Objects.isNull(configVo.getId())) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        projectService.configProject(configVo);
        return success();
    }

    /**
     * 获取项目配置
     *
     * @return Void
     */
    @GetMapping("/findConfigById")
    public ApiResult<ProjectConfigResponseVo> findConfigById(Integer id) {
        if (Objects.isNull(id)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        ProjectConfigResponseVo projectConfigResponseVo = projectService.findConfigById(id);
        return success(projectConfigResponseVo);
    }

}
