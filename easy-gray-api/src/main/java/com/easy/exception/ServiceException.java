package com.easy.exception;

import com.easy.domain.enumx.FailureEnum;
import lombok.Getter;

/**
 * service 层异常处理
 */
@Getter
public class ServiceException extends RuntimeException {

    private final FailureEnum failureEnum;

    public ServiceException(FailureEnum failureEnum) {

        super(failureEnum.getMsg());
        this.failureEnum = failureEnum;

    }
}
