package com.easy.api.exception;

import com.easy.api.domain.enumx.FailureEnum;
import lombok.Getter;

/**
 * easy异常基类
 *
 * @author liupenghao
 */
@Getter
public class BaseEasyException extends RuntimeException {

    private final FailureEnum failureEnum;

    public BaseEasyException(FailureEnum failureEnum) {

        super(failureEnum.getMessage());
        this.failureEnum = failureEnum;

    }
}
