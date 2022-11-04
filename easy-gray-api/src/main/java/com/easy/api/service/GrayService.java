package com.easy.api.service;

import com.alibaba.fastjson.JSON;
import com.easy.api.domain.entity.GrayEnvEntity;
import com.easy.api.domain.enumx.StateEnum;
import com.easy.api.domain.vo.GithubProjectVo;
import com.easy.api.domain.vo.GitlabProjectVo;
import com.easy.api.domain.vo.GrayEnvExtObjVo;
import com.easy.api.domain.vo.response.*;
import com.easy.api.exception.ServiceException;
import com.easy.api.mapper.GrayEnvMapper;
import com.easy.api.util.EasyHttp;
import com.easy.api.util.GitUtil;
import com.easy.core.enumx.FailureEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class GrayService {

    @Value("${git.github.username:}")
    private String githubUsername;

    @Value("${git.github.password:}")
    private String githubPassword;

    @Value("${git.github.repository_project_find_url:https://api.github.com/user/repos?sort=updated&direction=desc}")
    private String githubRepositoryProjectFindUrl;


    @Value("${git.gitlab.username:}")
    private String gitlabUsername;

    @Value("${git.gitlab.password:}")
    private String gitlabPassword;

    @Value("${git.gitlab.repository_project_find_url:https://git.uino.com/api/v4/projects?membership=true}")
    private String gitlabRepositoryProjectFindUrl;

    @Autowired
    private GrayEnvMapper grayEnvMapper;

    public Integer addGrayEnv(String name, LocalDateTime expireTime) {
        GrayEnvEntity saveEntity = new GrayEnvEntity();
        saveEntity.setName(name);
        saveEntity.setExpireTime(expireTime);

        grayEnvMapper.insertSelective(saveEntity);
        return saveEntity.getId();
    }

    public List<GitProjectResponseVo> findRepositoryProject(Integer gitType){
        List<GitProjectResponseVo> returnVoList = new ArrayList<>();

       if(gitType == 1){
           Map<String, String> headerMap = new HashMap<>();
           headerMap.put("Accept","application/vnd.github+json");
           headerMap.put("Authorization","Bearer " + githubPassword);
           List<GithubProjectVo> voList = EasyHttp.httpGetArray(githubRepositoryProjectFindUrl, headerMap, GithubProjectVo.class);

           for (GithubProjectVo loopVo : voList) {
               GitProjectResponseVo gitProjectVo = new GitProjectResponseVo();
               BeanUtils.copyProperties(loopVo, gitProjectVo);
               returnVoList.add(gitProjectVo);
           }
           return returnVoList;
       }else{
           Map<String, String> headerMap = new HashMap<>();
           headerMap.put("PRIVATE-TOKEN",gitlabPassword);
           List<GitlabProjectVo> voList = EasyHttp.httpGetArray(gitlabRepositoryProjectFindUrl, headerMap, GitlabProjectVo.class);

           for (GitlabProjectVo loopVo : voList) {
               GitProjectResponseVo gitProjectVo = new GitProjectResponseVo();
               BeanUtils.copyProperties(loopVo, gitProjectVo);
               returnVoList.add(gitProjectVo);
           }
           return returnVoList;
       }
    }

    public List<String> findProjectBranch(Integer gitType,String projectUrl) {
        CredentialsProvider credentialsProvider = getCredentialsProvider(gitType);
        try {
            return GitUtil.getRemoteBranches(credentialsProvider,projectUrl);
        } catch (GitAPIException e) {
            throw new ServiceException(FailureEnum.GIT_FETCH_EXCEPTION);
        }
    }

    public CredentialsProvider getCredentialsProvider(Integer gitType){
        String username = githubUsername;
        String password = githubPassword;
        if(gitType == 2){
            username = gitlabUsername;
            password = gitlabPassword;
        }
        return GitUtil.createPwdCredential(username, password);
    }

    /**
     * 添加项目到灰度环境
     */
    public void addProjectToGrayEnv(Integer id, String projectName, String projectBranch,String projectCloneUrl) {
        GrayEnvEntity grayEnvEntity = grayEnvMapper.selectByPrimaryKey(id);
        if(Objects.isNull(grayEnvEntity) || !Objects.equals(grayEnvEntity.getState(), StateEnum.NORMAL.getValue())){
            throw new ServiceException(FailureEnum.GIT_FETCH_EXCEPTION);
        }

        List<GrayEnvExtObjVo> extObjVoList = new ArrayList<>();
        String extObj = grayEnvEntity.getExtObj();
        if(StringUtils.isNotBlank(extObj)){
            extObjVoList = JSON.parseArray(extObj,GrayEnvExtObjVo.class);
            Optional<GrayEnvExtObjVo> voOptional = extObjVoList.stream().filter(loopVo -> Objects.equals(loopVo.getName(), projectName)).findFirst();
            voOptional.ifPresent(extObjVoList::remove);
        }

        // 更新 此项目信息
        // 封装 灰度扩展项目Vo
        GrayEnvExtObjVo extObjVo = new GrayEnvExtObjVo();
        extObjVo.setName(projectName);
        extObjVo.setBranch(projectBranch);
        extObjVo.setCloneUrl(projectCloneUrl);
        extObjVoList.add(extObjVo);

        GrayEnvEntity updateEntity = new GrayEnvEntity();
        updateEntity.setId(id);
        updateEntity.setExtObj(JSON.toJSONString(extObjVoList));

        grayEnvMapper.updateByPrimaryKeySelective(updateEntity);
    }

    public List<GrayEnvResponseVo> findAllGrayEnv() {
        List<GrayEnvEntity> grayEnvEntityList = grayEnvMapper.selectByState(StateEnum.NORMAL.getValue());
        if(CollectionUtils.isEmpty(grayEnvEntityList)){
            return Collections.emptyList();
        }

        List<GrayEnvResponseVo> returnList = new ArrayList<>();
        for (GrayEnvEntity loopEntity : grayEnvEntityList) {
            GrayEnvResponseVo envVo = new GrayEnvResponseVo();
            BeanUtils.copyProperties(loopEntity,envVo);
            String extObj = loopEntity.getExtObj();

            if(StringUtils.isNotBlank(extObj)){
                envVo.setExtObjList(JSON.parseArray(extObj,GrayEnvExtObjVo.class));
            }
            returnList.add(envVo);
        }
        return returnList;
    }
}
