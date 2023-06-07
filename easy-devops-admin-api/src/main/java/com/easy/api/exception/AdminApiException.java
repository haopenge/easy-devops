package com.easy.api.exception;

import com.easy.core.exception.BaseEasyException;
import com.easy.core.exception.ExceptionEnumInterface;
import lombok.Getter;

/**
 * easy异常基类
 *
 * @author liupenghao
 */
@Getter
public class AdminApiException extends BaseEasyException {

    public AdminApiException(ExceptionEnumInterface failureEnum) {
        super(failureEnum);
    }
}
