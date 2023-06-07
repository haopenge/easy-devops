package com.easy.core.audit.anno;

import com.easy.core.audit.CommonGenerator;
import com.easy.core.audit.DefaultCommonGenerator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 审计主键注解
 *
 * @author liuph
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface PrimaryKey {

    /**
     * 主键生成器
     *
     * @return 主键生成器
     */
    Class<? extends CommonGenerator> generator() default DefaultCommonGenerator.class;

}
