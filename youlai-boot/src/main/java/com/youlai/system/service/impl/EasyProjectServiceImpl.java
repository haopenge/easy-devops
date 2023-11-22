package com.youlai.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.system.common.enums.AdminApiFailureEnum;
import com.youlai.system.common.enums.GitRepositoryTypeEnum;
import com.youlai.system.common.exception.AdminApiException;
import com.youlai.system.config.properties.GlobalProperties;
import com.youlai.system.mapper.EasyCertificateMapper;
import com.youlai.system.mapper.EasyEnvMapper;
import com.youlai.system.mapper.EasyProjectMapper;
import com.youlai.system.mapper.EasyRepositoryMapper;
import com.youlai.system.model.entity.EasyCertificate;
import com.youlai.system.model.entity.EasyEnv;
import com.youlai.system.model.entity.EasyProject;
import com.youlai.system.model.entity.EasyRepository;
import com.youlai.system.model.vo.request.AddProjectRequestVo;
import com.youlai.system.model.vo.request.EditProjectRequestVo;
import com.youlai.system.model.vo.request.UpdateConfigVo;
import com.youlai.system.model.vo.response.ProjectConfigResponseVo;
import com.youlai.system.model.vo.response.ProjectResponseVo;
import com.youlai.system.service.EasyProjectService;
import com.youlai.system.service.IGitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author liupenghao
 * @description 针对表【easy_project(项目)】的数据库操作Service实现
 * @createDate 2023-08-17 11:42:09
 */
@Slf4j
@Service
public class EasyProjectServiceImpl extends ServiceImpl<EasyProjectMapper, EasyProject>
        implements EasyProjectService {

    @Resource
    private EasyEnvMapper envMapper;

    @Resource
    private EasyProjectMapper projectMapper;

    @Resource
    private EasyCertificateMapper certificateMapper;

    @Resource
    private EasyRepositoryMapper repositoryMapper;

    @Resource
    private GlobalProperties globalProperties;

    public Integer addProject(AddProjectRequestVo requestVo) {
        // 校验环境
        Integer envId = requestVo.getEnvId();
        if (Objects.nonNull(envId)) {
            EasyEnv envEntity = envMapper.selectById(envId);
            if (Objects.isNull(envEntity)) {
                throw new AdminApiException(AdminApiFailureEnum.GRAY_ENV_NOT_EXIST);
            }
        } else {
            envId = 0;
        }

        // 校验此项目是否已存在
        EasyProject dbProject = projectMapper.selectOne(new LambdaQueryWrapper<EasyProject>()
                .eq(EasyProject::getName, requestVo.getName())
                .eq(EasyProject::getEasyEnvId, envId)
        );
        if (Objects.nonNull(dbProject)) {
            throw new AdminApiException(AdminApiFailureEnum.ENV_PROJECT_EXIST);
        }

        // 校验仓库
        Integer repositoryId = requestVo.getEasyRepositoryId();
        EasyRepository repositoryEntity = repositoryMapper.selectById(repositoryId);
        if (Objects.isNull(repositoryEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.REPOSITORY_NOT_EXISTS);
        }

        // 新增项目
        EasyProject projectEntity = new EasyProject();
        projectEntity.setName(requestVo.getName());
        projectEntity.setEasyEnvId(envId);
        projectEntity.setEasyRepositoryId(repositoryId);
        projectEntity.setBranch(requestVo.getBranch());
        projectMapper.insert(projectEntity);

        return projectEntity.getId();
    }

    public void editProject(EditProjectRequestVo requestVo) {
        // 校验环境
        Integer envId = requestVo.getEnvId();
        if (Objects.nonNull(envId)) {
            EasyEnv envEntity = envMapper.selectById(envId);
            if (Objects.isNull(envEntity)) {
                throw new AdminApiException(AdminApiFailureEnum.GRAY_ENV_NOT_EXIST);
            }
        } else {
            envId = 0;
        }

        EasyProject projectEntity = projectMapper.selectById(requestVo.getId());
        if (Objects.isNull(projectEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.ENV_PROJECT_NOT_EXIST);
        }

        // 校验仓库
        Integer repositoryId = requestVo.getEasyRepositoryId();
        EasyRepository repositoryEntity = repositoryMapper.selectById(repositoryId);
        if (Objects.isNull(repositoryEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.REPOSITORY_NOT_EXISTS);
        }

        // 新增项目
        EasyProject updateEntity = new EasyProject();
        updateEntity.setId(requestVo.getId());
        updateEntity.setName(requestVo.getName());
        updateEntity.setEasyEnvId(envId);
        updateEntity.setEasyRepositoryId(repositoryId);
        updateEntity.setBranch(requestVo.getBranch());
        projectMapper.updateById(updateEntity);
    }

    public void deleteById(List<Integer> idList) {
        // TODO 校验服务是否已在运行
        projectMapper.deleteBatchIds(idList);
    }

    public List<String> findBranches(Integer easyRepositoryId) {
        EasyRepository repositoryEntity = repositoryMapper.selectById(easyRepositoryId);
        if (Objects.isNull(repositoryEntity)) {
            throw new AdminApiException(AdminApiFailureEnum.REPOSITORY_NOT_EXISTS);
        }
        EasyCertificate certificateEntity = certificateMapper.selectById(repositoryEntity.getEasyCertificateId());
        if (Objects.isNull(certificateEntity) || !GitRepositoryTypeEnum.existEnum(certificateEntity.getRepositoryType())) {
            throw new AdminApiException(AdminApiFailureEnum.CERTIFICATE_NOT_EXISTS);
        }
        IGitService gitService = GitRepositoryTypeEnum.getGitServiceByValue(certificateEntity.getRepositoryType());
        return gitService.getRemoteBranches(certificateEntity.getUsername(), repositoryEntity.getName(), certificateEntity.getAccessToken());
    }

    public List<ProjectResponseVo> findAll(Integer envId) {
        if (envId != 0) {
            EasyEnv envEntity = envMapper.selectById(envId);
            if (Objects.isNull(envEntity)) {
                throw new AdminApiException(AdminApiFailureEnum.GRAY_ENV_NOT_EXIST);
            }
        }
        List<EasyProject> projectEntities = projectMapper.selectList(new LambdaQueryWrapper<>());
        if (CollectionUtils.isEmpty(projectEntities)) {
            return Collections.emptyList();
        }

        List<EasyCertificate> certificateEntities = certificateMapper.selectList(new LambdaQueryWrapper<>());
        Map<Integer, EasyCertificate> certificateMap = certificateEntities.stream().collect(Collectors.toMap(EasyCertificate::getId, Function.identity()));

        List<EasyRepository> repositoryEntityList = repositoryMapper.selectList(new LambdaQueryWrapper<>());
        Map<Integer, EasyRepository> repositoryMap = repositoryEntityList.stream().collect(Collectors.toMap(EasyRepository::getId, Function.identity()));

        List<ProjectResponseVo> dataList = new ArrayList<>();
        for (EasyProject loopProject : projectEntities) {
            ProjectResponseVo responseVo = BeanUtil.toBean(loopProject, ProjectResponseVo.class);

            Integer easyRepositoryId = loopProject.getEasyRepositoryId();
            EasyRepository repositoryEntity = repositoryMap.get(easyRepositoryId);
            if (Objects.nonNull(repositoryEntity)) {
                responseVo.setEasyRepositoryName(repositoryEntity.getName());

                EasyCertificate certificateEntity = certificateMap.get(repositoryEntity.getEasyCertificateId());
                if (Objects.nonNull(certificateEntity)) {
                    responseVo.setEasyCertificateName(certificateEntity.getName());
                }
            }
            dataList.add(responseVo);
        }
        return dataList;
    }

    /**
     * 配置项目
     *
     * @param configVo 配置vo
     */
    public void configProject(UpdateConfigVo configVo) {
        Integer id = configVo.getId();

        saveScript(configVo.getDockerScript(), id, "docker");
        saveScript(configVo.getPushScript(), id, "push");

        String easyTemplateIds = configVo.getEasyTemplateIds();
        EasyProject updateDo = new EasyProject();
        updateDo.setId(id);
        updateDo.setEasyTemplateIds(easyTemplateIds);
        projectMapper.updateById(updateDo);

    }

    private void saveScript(String script, Integer id, String type) {
        String scriptPath = globalProperties.getScriptPath();
        String name = String.format(scriptPath + File.separator + "%s" + File.separator + "%s.sh", id, type);

        String dir = FileUtil.getParent(name, 1);
        if (!FileUtil.isDirectory(dir)) {
            FileUtil.mkdir(dir);
        }

        try (ByteArrayInputStream bais = new ByteArrayInputStream(script.getBytes(StandardCharsets.UTF_8));
             FileOutputStream fos = new FileOutputStream(name);
        ) {
            IoUtil.copy(bais, fos);
        } catch (Exception e) {
            log.error("save project script error ,id = {} , ", id, e);
            throw new AdminApiException(AdminApiFailureEnum.PROJECT_SCRIPT_SAVE_FAIL);
        }
    }

    public ProjectConfigResponseVo findConfigById(Integer id) {
        EasyProject easyProject = projectMapper.selectById(id);
        if (Objects.isNull(id)) {
            throw new AdminApiException(AdminApiFailureEnum.PROJECT_NOT_EXIST);
        }

        String dockerScript = getScript(id, "docker");
        String pushScript = getScript(id, "push");

        ProjectConfigResponseVo responseVo = new ProjectConfigResponseVo();
        responseVo.setId(id);
        responseVo.setEasyTemplateIds(easyProject.getEasyTemplateIds());
        responseVo.setDockerScript(dockerScript);
        responseVo.setPushScript(pushScript);
        return responseVo;

    }

    private String getScript(Integer id, String type) {
        String name = String.format(globalProperties.getScriptPath() + File.separator + "%s" + File.separator + "%s.sh", id, type);
        try (FileInputStream fis = new FileInputStream(name);
        ) {
            return IoUtil.read(fis, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("getScript error ,id = {} , type = {} ", id, type, e);
           // throw new AdminApiException(AdminApiFailureEnum.PROJECT_SCRIPT_FIND_FAIL);
            return StringUtils.EMPTY;
        }
    }
}




