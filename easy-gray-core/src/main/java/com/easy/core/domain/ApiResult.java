package com.easy.core.domain;

import cn.hutool.http.HttpStatus;
import com.easy.core.enumx.FailureEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果封装
 */
@Data
public class ApiResult<T> {

    private int code;

    private T data;

    private boolean success;

    private String message;

    public static ApiResult<Void> ok() {
        ApiVoidResult result = new ApiVoidResult();
        result.setSuccess(true);
        result.setCode(HttpStatus.HTTP_OK);
        return result;
    }

    public static <T> ApiResult<T> ok(T t) {
        ApiObjectResult result = new ApiObjectResult();
        result.setCode(HttpStatus.HTTP_OK);
        result.setSuccess(true);
        result.setData(t);
        return (ApiResult<T>) result;
    }

    public ApiResult<T> data(T data) {
        this.data = data;
        return this;
    }

    public static ApiResult<Object> error() {
        ApiObjectResult result = new ApiObjectResult();
        result.setSuccess(false);
        return result;
    }

    public static ApiResult<Void> error(FailureEnum failureEnum) {
        ApiVoidResult result = new ApiVoidResult();
        result.setSuccess(false);
        result.setCode(failureEnum.getCode());
        result.setMessage(failureEnum.getMessage());
        return result;
    }

    public static ApiResult<Void> error(int code,String message) {
        ApiVoidResult result = new ApiVoidResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    @NoArgsConstructor
    public static class ApiObjectResult extends ApiResult<Object> {

    }

    @NoArgsConstructor
    public static class ApiVoidResult extends ApiResult<Void> {

    }
}

