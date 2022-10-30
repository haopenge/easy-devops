package com.easy.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果封装
 */
@Data
public class ApiResult<T> {

    private Integer code;

    private T data;

    private boolean success;

    private String message;

    public static ApiResult<Object> ok() {
        ApiObjectResult result = new ApiObjectResult();
        result.setSuccess(true);
        return result;
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

    public static ApiObjectResult error(FailureEnum failureEnum) {
        ApiObjectResult result = new ApiObjectResult();
        result.setSuccess(false);
        result.setCode(failureEnum.getCode());
        result.setMessage(failureEnum.getMsg());
        return result;
    }

    @NoArgsConstructor
    public static class ApiObjectResult extends ApiResult<Object> {

    }
}

