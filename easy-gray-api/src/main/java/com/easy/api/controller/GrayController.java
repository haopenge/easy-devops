package com.easy.api.controller;

import com.easy.api.domain.vo.request.AddProjectToGrayEnvRequestVo;
import com.easy.api.domain.vo.request.GrayAddRequestVo;
import com.easy.api.domain.vo.response.GitProjectResponseVo;
import com.easy.api.domain.vo.response.GrayEnvResponseVo;
import com.easy.api.service.GrayService;
import com.easy.core.controller.BaseController;
import com.easy.core.domain.ApiResult;
import com.easy.core.enumx.FailureEnum;
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
        String projectName = addProjectToGrayEnv.getProjectName();
        String projectBranch = addProjectToGrayEnv.getProjectBranch();
        String projectCloneUrl = addProjectToGrayEnv.getProjectCloneUrl();

        if(StringUtils.isAnyBlank(projectName,projectBranch,projectCloneUrl) || Objects.isNull(id) || id <= 0){
            return failure(FailureEnum.PARAM_ERROR);
        }
        grayService.addProjectToGrayEnv(id,projectName,projectBranch,projectCloneUrl);
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
    public ApiResult<List<GitProjectResponseVo>> findProject(@RequestParam(name = "gitType",defaultValue = "1",required = false) Integer gitType){
        List<GitProjectResponseVo> voList = grayService.findRepositoryProject(gitType);
        return success(voList);
    }

    /**
     * 获取分支列表
     */
    @GetMapping("/find_project_branch")
    public ApiResult<List<String>> findProjectBranch(@RequestParam(name = "gitType",defaultValue = "1",required = false) Integer gitType,
                                                     String projectUrl){
        List<String> remoteBranches = grayService.findProjectBranch(gitType,projectUrl);
        return success(remoteBranches);
    }
}
