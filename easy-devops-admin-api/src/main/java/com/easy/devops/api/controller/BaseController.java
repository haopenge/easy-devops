package com.easy.devops.api.controller;

import com.easy.core.domain.ApiResult;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    public ApiResult<Void> success() {
        return ApiResult.ok();
    }

    public <T> ApiResult<T> success(T t) {
        return ApiResult.ok(t);
    }
}
