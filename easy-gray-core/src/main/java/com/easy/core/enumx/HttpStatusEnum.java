package com.easy.core.enumx;

import lombok.Getter;

/**
 * @author liuph
 */
@Getter
public enum HttpStatusEnum {
    OK(200, "响应成功"),

    FOUND(302,  "重定向"),

    UNAUTHORIZED(401,  "未认证"),

    ;
    private int status;

    private String description;

    HttpStatusEnum(int status, String description) {
        this.status = status;
        this.description = description;
    }
}
