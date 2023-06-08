package com.easy.devops.api.domain.enumx;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 凭证类型
 *
 * @author liuph
 */
@Getter
public enum AuthenticateTypeEnum {

    SSH_PRIVATE_KEY(1, "全局ssh私钥"),

    USER_PWD(5, "账号密码");

    private final int value;

    private final String describe;

    AuthenticateTypeEnum(int value, String describe) {
        this.value = value;
        this.describe = describe;
    }

    public static boolean existEnum(int value) {
        return Arrays.stream(AuthenticateTypeEnum.values()).anyMatch(loopEnum -> Objects.equals(loopEnum.value, value));
    }
}
