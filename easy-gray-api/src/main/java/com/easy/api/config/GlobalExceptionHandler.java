package com.easy.api.config;

import com.easy.api.controller.BaseController;
import com.easy.api.exception.AdminApiException;
import com.easy.core.domain.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler(AdminApiException.class)
    public ResponseEntity<Object> serviceException(HttpServletResponse response, AdminApiException exception) {
        log.error("GlobalExceptionHandler.runtimeException,msg: {}", exception.getMessage(), exception);
        return buildResponseEntity(response, ApiResult.error("000001", exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runtimeException(HttpServletResponse response, RuntimeException exception) {
        log.error("GlobalExceptionHandler.runtimeException,msg: {}", exception.getMessage(), exception);
        return buildResponseEntity(response, ApiResult.error("000002", exception.getMessage()));
    }


    /**
     * 建立响应实体
     *
     * @param result   结果
     * @param response 响应
     * @return {@link ResponseEntity}<{@link Object}>
     */
    ResponseEntity<Object> buildResponseEntity(HttpServletResponse response, Object result) {
        return response.isCommitted() ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build() : ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
