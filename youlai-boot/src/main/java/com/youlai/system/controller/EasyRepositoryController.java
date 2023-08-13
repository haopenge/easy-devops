package com.youlai.system.controller;


import com.easy.core.controller.BaseController;
import com.easy.core.domain.ApiResult;
import com.youlai.system.common.enums.AdminApiFailureEnum;
import com.youlai.system.common.exception.AdminApiException;
import com.youlai.system.model.vo.request.AddRepositoryRequestVo;
import com.youlai.system.model.vo.request.EditRepositoryRequestVo;
import com.youlai.system.model.vo.response.GitCertificateResponseVo;
import com.youlai.system.model.vo.response.RepositoryResponseVo;
import com.youlai.system.service.impl.EasyRepositoryServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 仓库
 *
 * @author liupenghao
 */
@RequestMapping("/repository")
@RestController
public class EasyRepositoryController extends BaseController {

    @Resource
    private EasyRepositoryServiceImpl easyRepositoryService;


    /**
     * 获取仓库列表
     *
     * @param certificateId 凭证id
     * @return GitCertificateResponseVo
     */
    @GetMapping("/findGitRepositories")
    public ApiResult<List<GitCertificateResponseVo>> findGitRepositories(Integer certificateId) {
        if (Objects.isNull(certificateId)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        List<GitCertificateResponseVo> dataList = easyRepositoryService.findGitRepositories(certificateId);
        return success(dataList);
    }

    /**
     * 新增仓库信息
     */
    @PostMapping("/add")
    public ApiResult<Integer> add(@RequestBody AddRepositoryRequestVo requestVo) {
        String name = requestVo.getName();
        Integer easyCertificateId = requestVo.getEasyCertificateId();

        if (StringUtils.isBlank(name) || Objects.isNull(easyCertificateId)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        Integer id = easyRepositoryService.add(requestVo);
        return success(id);
    }

    /**
     * 修改仓库信息
     */
    @PutMapping("/edit")
    public ApiResult<Void> edit(@RequestBody EditRepositoryRequestVo requestVo) {
        Integer id = requestVo.getId();
        if (Objects.isNull(id)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        easyRepositoryService.edit(requestVo);
        return success();
    }

    /**
     * 删除仓库信息
     */
    @DeleteMapping("/deleteById")
    public ApiResult<Void> deleteById(String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        List<Integer> idList = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        easyRepositoryService.deleteById(idList);
        return success();
    }


    /**
     * 获取仓库信息
     */
    @GetMapping("/findAll")
    public ApiResult<List<RepositoryResponseVo>> findAll() {
        List<RepositoryResponseVo> data = easyRepositoryService.findAll();
        return success(data);
    }


}
