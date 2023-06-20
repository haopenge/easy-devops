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
public enum TemplateTypeEnum {

    DOCKERFILE(1, "Dockerfile"),
    SHELL(5, "Shell"),
    YAML(10, "Yaml"),
    JSON(11, "Json"),
    PROPERTIES(12, "Properties"),
    XML(13, "Xml"),

    ;

    private final int value;

    private final String description;

    TemplateTypeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static boolean existEnum(int value) {
        return Arrays.stream(TemplateTypeEnum.values()).anyMatch(loopEnum -> Objects.equals(loopEnum.value, value));
    }

    public static TemplateTypeEnum valueOf(int value) {
        return Arrays.stream(TemplateTypeEnum.values()).filter(loopEnum -> Objects.equals(loopEnum.value, value)).findFirst().orElseGet(null);
    }
}
