package com.easy.api.controller;

import com.easy.api.domain.enumx.FailureEnum;
import com.easy.api.domain.vo.request.AddProjectToGrayEnvRequestVo;
import com.easy.api.domain.vo.request.GrayAddRequestVo;
import com.easy.api.domain.vo.response.GitProjectResponseVo;
import com.easy.api.domain.vo.response.GrayEnvResponseVo;
import com.easy.api.exception.ServiceException;
import com.easy.api.service.GrayService;
import com.easy.api.service.IGitService;
import com.easy.core.domain.ApiResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RequestMapping("/gray")
@RestController
public class GrayController extends BaseController {

    @Autowired
    private GrayService grayService;

    @Autowired
    private IGitService gitService;

    /**
     * 新增灰度环境
     */
    @PostMapping("/add")
    public ApiResult<Integer> addGrayEnv(@RequestBody GrayAddRequestVo requestVo) {
        if (StringUtils.isBlank(requestVo.getName())) {
            return failure(FailureEnum.PARAM_ERROR);
        }
        Integer grayEnvId = grayService.addGrayEnv(requestVo.getName(), requestVo.getExpireTime());
        return success(grayEnvId);
    }

    /**
     * 删除灰度环境
     */
    @DeleteMapping("/deleteById")
    public ApiResult<Void> deleteById(@RequestParam Integer id) {
        grayService.deleteById(id);
        return success();
    }

    /**
     * 添加项目灰度环境
     */
    @PostMapping("/addProjectToGrayEnv")
    public ApiResult<Void> addProjectToGrayEnv(@RequestBody AddProjectToGrayEnvRequestVo addProjectToGrayEnv){

        Integer id = addProjectToGrayEnv.getId();
        String fullName = addProjectToGrayEnv.getFullName();
        String subProjectPath = addProjectToGrayEnv.getSubProjectPath();
        String branchName = addProjectToGrayEnv.getBranchName();

        grayService.addProjectToGrayEnv(id, fullName, subProjectPath,branchName);
        return success();
    }

    /**
     * 删除灰度环境的项目
     */
    @DeleteMapping ("/deleteProjectInGrayEnv")
    public ApiResult<Void> deleteProjectInGrayEnv(@RequestParam Integer id,
                                                  @RequestParam String projectId){
        if(Objects.isNull(id) || StringUtils.isBlank(projectId)){
            throw new ServiceException(FailureEnum.PARAM_ERROR);
        }
        grayService.deleteProjectInGrayEnv(id, projectId);
        return success();
    }

    @RequestMapping("/runProjectInGrayEnv")
    public ApiResult<Void> runProjectInGrayEnv(@RequestParam("id") Integer id,
                                               @RequestParam("projectId") String projectId) {
        if (id <= 0 || StringUtils.isBlank(projectId)) {
            throw new ServiceException(FailureEnum.PARAM_ERROR);
        }

        grayService.runProjectInGrayEnv(id, projectId);
        return success();
    }

    /**
     * 获取灰度环境
     */
    @GetMapping("/findAll")
    public ApiResult<List<GrayEnvResponseVo>> findAllGrayEnv() {
        List<GrayEnvResponseVo> grayEnvVoList = grayService.findAllGrayEnv();
        return success(grayEnvVoList);
    }

    /**
     * 获取git 仓库项目列表
     */
    @GetMapping("/findProject")
    public ApiResult<List<GitProjectResponseVo>> findProject() {
        List<GitProjectResponseVo> voList = gitService.findRepositoryProject();
        return success(voList);
    }

    @GetMapping("/findProjectByFullName")
    public ApiResult<GitProjectResponseVo> findProject(String fullName) {
        GitProjectResponseVo gitProjectResponseVo = gitService.findRepositoryByFullName(fullName);
        return success(gitProjectResponseVo);
    }

    /**
     * 获取分支列表
     */
    @GetMapping("/findProjectBranch")
    public ApiResult<List<String>> findProjectBranch(String projectUrl) {
        List<String> remoteBranches = gitService.getRemoteBranches(projectUrl);
        return success(remoteBranches);
    }
}
