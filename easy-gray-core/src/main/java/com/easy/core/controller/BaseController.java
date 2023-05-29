package com.easy.core.controller;

import com.easy.core.domain.ApiResult;
import com.easy.core.enumx.BaseFailureEnum;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {


    public ApiResult<Void> success(){
        return ApiResult.ok();
    }

    public <T> ApiResult<T> success(T t){
        return ApiResult.ok(t);
    }

    public ApiResult failure(){
        return ApiResult.error();
    }

    public ApiResult failure(BaseFailureEnum failureEnum){
        ApiResult.ApiVoidResult result = new ApiResult.ApiVoidResult();
        result.setSuccess(false);
        result.setCode(failureEnum.getCode());
        result.setMessage(failureEnum.getMessage());
        return result;
    }

    public ApiResult failure(String code,String message){
        return ApiResult.error(code,message);
    }
}
