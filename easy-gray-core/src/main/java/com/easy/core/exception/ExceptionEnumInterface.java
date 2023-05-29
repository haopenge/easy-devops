package com.easy.core.exception;

/**
 * @author liuph
 */
public interface ExceptionEnumInterface {

    /**
     * 获取异常信息
     *
     * @return message
     */
    String getMessage();

    /**
     * 获取异常code
     *
     * @return 异常code
     */
    String getCode();
}
