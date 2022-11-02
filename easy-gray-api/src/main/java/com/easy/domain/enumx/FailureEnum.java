package com.easy.domain.enumx;

import lombok.Getter;

/**
 * 请求失败状态信息定义
 * @author liuph
 */
@Getter
public enum FailureEnum {

    // ****************************************** 服务内部错误 100_000 ~ 100_099 *************************************************************
    AUTH_TOKEN_EXPIRED(100_000,"token失效,请重新登录"),

    SYSTEM_INNER_ERROR(100_001,"服务内部错误: "),

    FEIGN_INVOKE_ERROR(100_002,"远程调用失败"),
    ;

    private int code;

    private String msg;

    FailureEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
