package com.youlai.system.controller;

import com.easy.core.controller.BaseController;
import com.easy.core.domain.ApiResult;
import com.youlai.system.common.enums.AdminApiFailureEnum;
import com.youlai.system.common.exception.AdminApiException;
import com.youlai.system.model.vo.request.AddTemplateRequestVo;
import com.youlai.system.model.vo.response.TemplateResponseVo;
import com.youlai.system.service.impl.TemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;


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

    @GetMapping("/findAll")
    public ApiResult<List<TemplateResponseVo>> findAll() {
        List<TemplateResponseVo> dataList = templateService.findAll();
        return success(dataList);
    }

    @DeleteMapping("/deleteById")
    public ApiResult<Void> deleteById(Integer id) {
        templateService.deleteById(id);
        return success();
    }

}
