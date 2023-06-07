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

    USER_PWD(1, "账号密码"),
    SSH_PRIVATE_KEY(5, "ssh私钥");

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
