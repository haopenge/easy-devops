package com.easy.core.audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 实体注解属性缓存对象
 * @author liupenghao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnoField {

    /**
     * 属性
     */
    private Field field;

    /**
     * 审计注解,目前限定单个
     */
    private Annotation annotation;

    /**
     * 属性值生成器
     */
    private Class<? extends CommonGenerator> generator;

    public boolean equals(Object obj) {
        if (!(obj instanceof AnnoField)) {
            return false;
        }
        AnnoField tempAnnoField = (AnnoField) obj;
        return Objects.equals(this.field, tempAnnoField.field);
    }
}
