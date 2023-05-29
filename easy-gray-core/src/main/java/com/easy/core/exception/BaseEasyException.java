package com.easy.core.exception;

import lombok.Getter;

/**
 * easy异常基类
 *
 * @author liupenghao
 */
@Getter
public class BaseEasyException extends RuntimeException {

    private final ExceptionEnumInterface enumInterface;

    public BaseEasyException(ExceptionEnumInterface enumInterface) {
        super(enumInterface.getMessage());
        this.enumInterface = enumInterface;

    }
}
