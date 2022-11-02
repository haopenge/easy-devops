package com.easy.controller;

import com.easy.domain.enumx.FailureEnum;
import com.easy.domain.vo.ApiResult;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {


    public ApiResult<Void> success(){
        return ApiResult.ok();
    }

    public <T> ApiResult<T> success(T t){
        return ApiResult.ok(t);
    }

    public <T> ApiResult<T> failure(T t){
        return ApiResult.ok(t);
    }

    public ApiResult<Void> failure(FailureEnum failureEnum){
        return ApiResult.error(failureEnum);
    }

    public ApiResult<Void> failure(int code,String message){
        return ApiResult.error(code,message);
    }
}
