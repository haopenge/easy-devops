package com.easy.api.controller;

import com.easy.api.domain.enumx.FailureEnum;
import com.easy.api.domain.vo.request.GrayAddRequestVo;
import com.easy.api.domain.vo.request.GrayEditRequestVo;
import com.easy.api.domain.vo.response.GrayEnvResponseVo;
import com.easy.api.exception.BaseEasyException;
import com.easy.api.service.impl.GrayService;
import com.easy.core.domain.ApiResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RequestMapping("/env")
@RestController
public class EnvController extends BaseController {

    @Autowired
    private GrayService grayService;

    /**
     * 新增灰度环境
     */
    @PostMapping("/add")
    public ApiResult<Integer> addGrayEnv(@RequestBody GrayAddRequestVo requestVo) {
        if (StringUtils.isBlank(requestVo.getName())) {
            throw new BaseEasyException(FailureEnum.PARAM_ERROR);
        }
        Integer grayEnvId = grayService.addGrayEnv(requestVo);
        return success(grayEnvId);
    }

    /**
     * 更新灰度环境
     */
    @PostMapping("/edit")
    public ApiResult<Void> editGrayEnv(@RequestBody GrayEditRequestVo requestVo) {
        if (Objects.isNull(requestVo.getId())) {
            throw new BaseEasyException(FailureEnum.PARAM_ERROR);
        }
        grayService.editGrayEnv(requestVo);
        return success();
    }

    /**
     * 删除灰度环境
     */
    @DeleteMapping("/deleteById")
    public ApiResult<Void> deleteById(@RequestParam Integer id) {
        grayService.deleteById(id);
        return success();
    }

    /**
     * 获取灰度环境
     */
    @GetMapping("/findAll")
    public ApiResult<List<GrayEnvResponseVo>> findAllGrayEnv(HttpServletRequest request) {
        List<GrayEnvResponseVo> grayEnvVoList = grayService.findAllGrayEnv();
        return success(grayEnvVoList);
    }

}
