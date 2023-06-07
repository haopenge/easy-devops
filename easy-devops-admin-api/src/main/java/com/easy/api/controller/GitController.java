package com.easy.api.controller;

import com.easy.api.domain.vo.response.GitProjectResponseVo;
import com.easy.api.service.IGitService;
import com.easy.core.domain.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/git")
@RestController
public class GitController extends BaseController {

    @Autowired
    private IGitService gitService;

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
    @GetMapping("/findBranches")
    public ApiResult<List<String>> findProjectBranches(String projectUrl) {
        List<String> remoteBranches = gitService.getRemoteBranches(projectUrl);
        return success(remoteBranches);
    }
}
