package com.easy.api.controller;

import com.easy.api.domain.vo.request.AddProjectToGrayEnvRequestVo;
import com.easy.api.domain.vo.response.GitProjectResponseVo;
import com.easy.api.service.GrayService;
import com.easy.core.domain.ApiResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/project")
@RestController
public class ProjectController extends BaseController {

    @Resource
    private GrayService grayService;

    /**
     * 删除灰度环境的项目
     */
    @DeleteMapping("/deleteById")
    public ApiResult<Void> deleteProjectInGrayEnv(@RequestParam Integer projectId) {
        grayService.deleteProjectInGrayEnv(projectId);
        return success();
    }

    /**
     * 添加项目灰度环境
     */
    @PostMapping("/add")
    public ApiResult<Void> addProjectToGrayEnv(@RequestBody AddProjectToGrayEnvRequestVo addProjectToGrayEnv) {

        Integer id = addProjectToGrayEnv.getId();
        String fullName = addProjectToGrayEnv.getFullName();
        String subProjectPath = addProjectToGrayEnv.getSubProjectPath();
        String branchName = addProjectToGrayEnv.getBranchName();

        grayService.addProjectToGrayEnv(id, fullName, subProjectPath, branchName);
        return success();
    }

    @RequestMapping("/run")
    public ApiResult<Void> runProjectInGrayEnv(Integer projectId) {
        grayService.runProjectInGrayEnv(projectId);
        return success();
    }

    /**
     * 获取环境中的项目
     *
     * @param envId 环境id
     * @return 项目
     */
    @RequestMapping("/findByEnvId")
    public ApiResult<List<GitProjectResponseVo>> findProject(Integer envId) {
        return success(grayService.findProject(envId));
    }

}
