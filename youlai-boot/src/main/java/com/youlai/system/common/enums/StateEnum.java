package com.youlai.system.common.enums;

import lombok.Getter;

/**
 * 显示状态枚举
 */
@Getter
public enum StateEnum {
    NORMAL(1,"正常"),
    RECYCLE(2,"回收站资源"),
    DELETED(3,"彻底删除资源")
    ;

    private  int value;

    private String describe;

    StateEnum(int value, String describe) {
        this.value = value;
        this.describe = describe;
    }
}
