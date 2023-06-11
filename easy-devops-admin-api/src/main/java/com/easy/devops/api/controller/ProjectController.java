package com.easy.devops.api.controller;

import com.easy.devops.api.domain.vo.request.AddProjectRequestVo;
import com.easy.devops.api.domain.vo.request.EditProjectRequestVo;
import com.easy.core.domain.ApiResult;
import com.easy.devops.api.domain.vo.response.ProjectResponseVo;
import com.easy.devops.api.service.impl.ProjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liupenghao
 */
@RequestMapping("/project")
@RestController
public class ProjectController extends BaseController {

    @Resource
    private ProjectService projectService;

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
    public ApiResult<Void> deleteById(@RequestParam Integer id) {
        projectService.deleteById(id);
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
    public ApiResult<List<ProjectResponseVo>> findAll(@RequestParam(required = false,defaultValue = "0") Integer envId) {
        List<ProjectResponseVo> dataList = projectService.findAll(envId);
        return success(dataList);
    }

    /**
     * 编辑项目
     */
    @PostMapping("/edit")
    public ApiResult<Void> editProject(@RequestBody EditProjectRequestVo editProjectRequestVo) {
        projectService.editProject(editProjectRequestVo);
        return success();
    }

    /**
     * 运行项目
     *
     * @param id 项目id
     * @return Void
     */
    @PostMapping("/run")
    public ApiResult<Void> runProjectInGrayEnv(Integer id) {
        //  grayService.runProjectInGrayEnv(id);
        return success();
    }

    /**
     * 停止项目
     *
     * @param id 项目id
     * @return Void
     */
    @PostMapping("/stop")
    public ApiResult<Void> stopProjectInGrayEnv(Integer id) {
        // grayService.stopProjectInGrayEnv(id);
        return success();
    }

}
