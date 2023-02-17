package com.easy.api.exception;

import com.easy.api.domain.enumx.FailureEnum;
import lombok.Getter;

/**
 * service 层异常处理
 */
@Getter
public class ServiceException extends RuntimeException {

    private final FailureEnum failureEnum;

    public ServiceException(FailureEnum failureEnum) {

        super(failureEnum.getMessage());
        this.failureEnum = failureEnum;

    }
}
