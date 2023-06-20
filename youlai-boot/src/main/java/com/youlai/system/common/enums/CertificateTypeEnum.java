package com.youlai.system.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 凭证类型
 *
 * @author liuph
 */
@Getter
public enum CertificateTypeEnum {

    SSH_PRIVATE_KEY(1, "全局ssh私钥"),

    K8S_KUBE_CONFIG(3,"k8s配置"),

    USER_PWD(5, "账号密码");

    private final int value;

    private final String describe;

    CertificateTypeEnum(int value, String describe) {
        this.value = value;
        this.describe = describe;
    }

    public static boolean existEnum(int value) {
        return Arrays.stream(CertificateTypeEnum.values()).anyMatch(loopEnum -> Objects.equals(loopEnum.value, value));
    }
}
