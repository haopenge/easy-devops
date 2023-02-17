package com.easy.api.controller;

import com.easy.api.domain.enumx.FailureEnum;
import com.easy.core.domain.ApiResult;
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

    public ApiResult failure(FailureEnum failureEnum){
        ApiResult.ApiVoidResult result = new ApiResult.ApiVoidResult();
        result.setSuccess(false);
        result.setCode(failureEnum.getCode());
        result.setMessage(failureEnum.getMessage());
        return result;
    }

    public ApiResult failure(int code,String message){
        return ApiResult.error(code,message);
    }
}
