package com.easy.config;

import com.easy.controller.BaseController;
import com.easy.domain.enumx.FailureEnum;
import com.easy.domain.vo.ApiResult;
import com.easy.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler(ServiceException.class)
    public ApiResult<Void> serviceException(ServiceException exception){
        FailureEnum failureEnum = exception.getFailureEnum();
        log.info("GlobalExceptionHandler.serviceException code: {},msg: {}",failureEnum.getCode(),failureEnum.getMsg());
        return failure(failureEnum);
    }

    @ExceptionHandler(RuntimeException.class)
    public ApiResult<Void> runtimeException(RuntimeException exception){
        log.error("GlobalExceptionHandler.runtimeException,msg: {}",exception.getMessage(),exception);
        return failure(FailureEnum.SYSTEM_INNER_ERROR.getCode(),FailureEnum.SYSTEM_INNER_ERROR.getMsg() + exception.getMessage());
    }

}
