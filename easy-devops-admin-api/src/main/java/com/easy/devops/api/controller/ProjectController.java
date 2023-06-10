package com.easy.devops.api.controller;

import com.easy.devops.api.domain.vo.request.AddProjectRequestVo;
import com.easy.devops.api.domain.vo.request.EditProjectRequestVo;
import com.easy.core.domain.ApiResult;
import com.easy.devops.api.service.impl.ProjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liupenghao
 */
@RequestMapping("/project")
@RestController
public class ProjectController extends BaseController {

    @Resource
    private ProjectService projectService;

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
    public ApiResult<Void> addProject(@RequestBody @Validated AddProjectRequestVo addProjectToGrayEnv) {
        projectService.addProject(addProjectToGrayEnv);
        return success();
    }

    /**
     * 编辑项目
     */
    @PostMapping("/edit")
    public ApiResult<Void> editProject(@RequestBody @Validated EditProjectRequestVo editProjectRequestVo) {
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
