package com.easy.core.audit;

import com.easy.core.audit.anno.Creator;
import com.easy.core.util.ClassUtil;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.Objects;


/**
 * 默认操作者-生成器
 *
 * @author liuph
 */
@Service
public class DefaultOperatorGenerator implements CommonGenerator<String> {

    /**
     * 默认值
     */
    public static final String DEFAULT_VALUE = "defaultValue";

    @Override
    public String generate(Object entity, AnnoField annoField, boolean isCreate) {
        Annotation annotation = annoField.getAnnotation();
        String defaultValue = (String) ClassUtil.getFieldAnnotationMemberValue(annotation, DEFAULT_VALUE);
        if (Objects.equals(annoField.getField().getType(), Creator.class)) {
            return isCreate ? getOperator(annotation, defaultValue) : defaultValue;
        }

        return getOperator(annotation, defaultValue);
    }

    /**
     * 获取创建者
     *
     * @param annotation 待注入属性审计注解
     * @return 创建者信息
     */
    private static String getOperator(Annotation annotation, String defaultValue) {
        // TODO 待补充

        return "admin";
    }
}
