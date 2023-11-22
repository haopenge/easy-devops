package com.youlai.system.controller;

import com.easy.core.controller.BaseController;
import com.easy.core.domain.ApiResult;
import com.youlai.system.common.enums.AdminApiFailureEnum;
import com.youlai.system.common.exception.AdminApiException;
import com.youlai.system.model.vo.request.AddTemplateRequestVo;
import com.youlai.system.model.vo.request.EditTemplateRequestVo;
import com.youlai.system.model.vo.response.TemplateResponseVo;
import com.youlai.system.service.impl.TemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 模板
 *
 * @author liupenghao
 */
@RequestMapping("/template")
@RestController
public class TemplateController extends BaseController {

    @Resource
    private TemplateService templateService;

    @PostMapping("/add")
    public ApiResult<Integer> add(@RequestBody AddTemplateRequestVo requestVo) {
        if (StringUtils.isAnyBlank(requestVo.getName(), requestVo.getContent()) || Objects.isNull(requestVo.getType())) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        Integer id = templateService.add(requestVo);
        return success(id);
    }

    @PutMapping("/edit")
    public ApiResult<Void> edit(@RequestBody EditTemplateRequestVo requestVo) {
        if (StringUtils.isAnyBlank(requestVo.getName(), requestVo.getContent()) || Objects.isNull(requestVo.getType())) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        templateService.edit(requestVo);
        return success();
    }

    @GetMapping("/findAll")
    public ApiResult<List<TemplateResponseVo>> findAll() {
        List<TemplateResponseVo> dataList = templateService.findAll();
        return success(dataList);
    }

    @DeleteMapping("/deleteById")
    public ApiResult<Void> deleteById(@RequestParam String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new AdminApiException(AdminApiFailureEnum.PARAM_ERROR);
        }
        List<Integer> idList = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        templateService.deleteById(idList);
        return success();
    }

}
