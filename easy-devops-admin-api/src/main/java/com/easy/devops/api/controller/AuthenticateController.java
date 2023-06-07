package com.easy.devops.api.controller;

import com.easy.core.domain.ApiResult;
import com.easy.devops.api.domain.enumx.AdminApiFailureEnum;
import com.easy.devops.api.domain.vo.request.AddAuthenticateRequestVo;
import com.easy.devops.api.domain.vo.request.EditAuthenticateRequestVo;
import com.easy.devops.api.domain.vo.response.AuthenticateResponseVo;
import com.easy.devops.api.exception.AdminApiException;
import com.easy.devops.api.service.impl.AuthenticateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 凭证
 *
 * @author liuph
 */
@RequestMapping("/authorizations")
@RestController
public class AuthenticateController extends BaseController {

    @Autowired
    private AuthenticateService authenticateService;

    /**
     * 新增凭证信息
     */
    @PostMapping("/add")
    public ApiResult<Integer> add(@RequestBody AddAuthenticateRequestVo requestVo) {
        String name = requestVo.getName();
        Integer type = requestVo.getType();
        if (StringUtils.isBlank(name) || Objects.isNull(type)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        Integer id = authenticateService.add(requestVo);
        return success(id);
    }

    /**
     * 修改凭证信息
     */
    @PostMapping("/edit")
    public ApiResult<Void> edit(@RequestBody EditAuthenticateRequestVo requestVo) {
        Integer type = requestVo.getType();
        if (Objects.isNull(type)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        authenticateService.edit(requestVo);
        return success();
    }

    /**
     * 删除凭证信息
     */
    @PostMapping("/deleteById")
    public ApiResult<Void> deleteById(Integer id) {
        if (Objects.isNull(id)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        authenticateService.deleteById(id);
        return success();
    }


    /**
     * 获取凭证信息
     */
    @PostMapping("/findAll")
    public ApiResult<List<AuthenticateResponseVo>> findAll() {
        List<AuthenticateResponseVo> data = authenticateService.findAll();
        return success(data);
    }

}
