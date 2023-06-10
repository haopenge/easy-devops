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
public enum GitRepositoryTypeEnum {

    GITHUB(1, "github"),

    GITEE(2, "gitee"),

    GITLAB(3, "gitlab");


    private final int value;

    private final String describe;

    GitRepositoryTypeEnum(int value, String describe) {
        this.value = value;
        this.describe = describe;
    }

    public static boolean existEnum(int value) {
        return Arrays.stream(GitRepositoryTypeEnum.values()).anyMatch(loopEnum -> Objects.equals(loopEnum.value, value));
    }

    public static GitRepositoryTypeEnum valueOf(int value) {
        return Arrays.stream(GitRepositoryTypeEnum.values()).filter(loopEnum -> Objects.equals(loopEnum.value, value)).findFirst().orElse(null);
    }
}
