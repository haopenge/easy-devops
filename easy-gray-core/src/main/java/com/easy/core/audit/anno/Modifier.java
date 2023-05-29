package com.easy.core.audit.anno;

import com.easy.core.audit.CommonGenerator;
import com.easy.core.audit.DefaultCommonGenerator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 变更-操作人
 *
 * @author liuph
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface Modifier {

    /**
     * 默认值，即按生成器生成失败时填写的值
     *
     * @return 默认空字符串
     */
    String defaultValue() default "0";


    /**
     * 日期生成器
     *
     * @return 主键生成器
     */
    Class<? extends CommonGenerator> generator() default DefaultCommonGenerator.class;
}
