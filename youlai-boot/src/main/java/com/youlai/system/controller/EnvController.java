package com.youlai.system.controller;

import com.easy.core.controller.BaseController;
import com.easy.core.domain.ApiResult;
import com.youlai.system.common.enums.AdminApiFailureEnum;
import com.youlai.system.common.exception.AdminApiException;
import com.youlai.system.model.vo.request.AddEnvRequestVo;
import com.youlai.system.model.vo.request.EditEnvRequestVo;
import com.youlai.system.model.vo.response.EnvResponseVo;
import com.youlai.system.service.impl.EasyEnvServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 环境
 *
 * @author liupenghao
 */
@RequestMapping("/env")
@RestController
public class EnvController extends BaseController {

    @Resource
    private EasyEnvServiceImpl envService;

    @PostMapping("/add")
    public ApiResult<Integer> add(@RequestBody AddEnvRequestVo requestVo) {
        if (StringUtils.isAnyBlank(requestVo.getName())) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        Integer id = envService.add(requestVo);
        return success(id);
    }

    @PutMapping("/edit")
    public ApiResult<Void> edit(@RequestBody EditEnvRequestVo requestVo) {
        if (StringUtils.isAnyBlank(requestVo.getName())) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        envService.edit(requestVo);
        return success();
    }

    @GetMapping("/findAll")
    public ApiResult<List<EnvResponseVo>> findAll() {
        List<EnvResponseVo> dataList = envService.findAll();
        return success(dataList);
    }

    @DeleteMapping("/deleteById")
    public ApiResult<Void> deleteById(@RequestParam String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        List<Integer> idList = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        envService.deleteById(idList);
        return success();
    }

}
