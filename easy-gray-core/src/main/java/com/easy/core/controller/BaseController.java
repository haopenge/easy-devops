package com.easy.core.controller;

import com.easy.core.domain.ApiResult;
import com.easy.core.enumx.FailureEnum;
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
        return ApiResult.error(failureEnum);
    }

    public ApiResult failure(int code,String message){
        return ApiResult.error(code,message);
    }
}
