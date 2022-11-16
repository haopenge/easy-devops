package com.easy.api.controller;

import com.easy.api.domain.enumx.FailureEnum;
import com.easy.api.domain.vo.request.AddProjectToGrayEnvRequestVo;
import com.easy.api.domain.vo.request.GrayAddRequestVo;
import com.easy.api.domain.vo.response.GitProjectResponseVo;
import com.easy.api.domain.vo.response.GrayEnvResponseVo;
import com.easy.api.exception.ServiceException;
import com.easy.api.service.GithubService;
import com.easy.api.service.GrayService;
import com.easy.core.domain.ApiResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequestMapping("/gray")
@RestController
public class GrayController extends BaseController {

    @Autowired
    private GrayService grayService;

    @Autowired
    private GithubService githubService;

    /**
     * 新增灰度环境
     */
    @PostMapping("/add")
    public ApiResult<Integer> addGrayEnv(@RequestBody GrayAddRequestVo requestVo){
        if(StringUtils.isBlank(requestVo.getName())){
            return failure(FailureEnum.PARAM_ERROR);
        }
        Integer grayEnvId = grayService.addGrayEnv(requestVo.getName(),requestVo.getExpireTime());
        return success(grayEnvId);
    }

    /**
     * 新增灰度环境
     */
    @PostMapping("/addProjectToGrayEnv/{id}")
    public ApiResult<Void> addProjectToGrayEnv(@PathVariable("id") Integer id,
                                               @RequestBody AddProjectToGrayEnvRequestVo addProjectToGrayEnv
                                         ){
        String name = addProjectToGrayEnv.getName();
        String branch = addProjectToGrayEnv.getBranch();
        String cloneUrl = addProjectToGrayEnv.getCloneUrl();
        String packagePath = addProjectToGrayEnv.getPackagePath();
        String gitName = addProjectToGrayEnv.getGitName();

        if(StringUtils.isAnyBlank(name,branch,cloneUrl) || Objects.isNull(id) || id <= 0){
            return failure(FailureEnum.PARAM_ERROR);
        }
        grayService.addProjectToGrayEnv(id,name,branch,cloneUrl,packagePath,gitName);
        return success();
    }


    @PostMapping("/runProjectInGrayEnv/{id}")
    public ApiResult<Void> runProjectInGrayEnv(@PathVariable("id") Integer id,
                                               @RequestParam("name") String name){
        if(id <= 0 || StringUtils.isBlank(name)){
            throw new ServiceException(FailureEnum.PARAM_ERROR);
        }

        grayService.runProjectInGrayEnv(id,name);
        return success();
    }



    /**
     * 获取灰度环境
     */
    @GetMapping("/findAll")
    public ApiResult<List<GrayEnvResponseVo>> findAllGrayEnv(){
        List<GrayEnvResponseVo> grayEnvVoList = grayService.findAllGrayEnv();
        return success(grayEnvVoList);
    }

    /**
     * 获取github项目列表
     */
    @GetMapping("/find_project")
    public ApiResult<List<GitProjectResponseVo>> findProject(){
        List<GitProjectResponseVo> voList = githubService.findRepositoryProject();
        return success(voList);
    }

    /**
     * 获取分支列表
     */
    @GetMapping("/find_project_branch")
    public ApiResult<List<String>> findProjectBranch(String projectUrl){
        List<String> remoteBranches = githubService.getRemoteBranches(projectUrl);
        return success(remoteBranches);
    }
}
