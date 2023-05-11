package com.easy.api.controller;

import com.easy.api.domain.enumx.FailureEnum;
import com.easy.api.domain.vo.request.GrayAddRequestVo;
import com.easy.api.domain.vo.response.GrayEnvResponseVo;
import com.easy.api.service.GrayService;
import com.easy.api.service.IGitService;
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

@RequestMapping("/env")
@RestController
public class EnvController extends BaseController {

    @Autowired
    private GrayService grayService;

    @Autowired
    private IGitService gitService;

    /**
     * 新增灰度环境
     */
    @PostMapping("/add")
    public ApiResult<Integer> addGrayEnv(@RequestBody GrayAddRequestVo requestVo) {
        if (StringUtils.isBlank(requestVo.getName())) {
            return failure(FailureEnum.PARAM_ERROR);
        }
        Integer grayEnvId = grayService.addGrayEnv(requestVo.getName(), requestVo.getExpireTime());
        return success(grayEnvId);
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
