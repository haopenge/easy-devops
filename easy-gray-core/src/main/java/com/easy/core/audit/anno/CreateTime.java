package com.easy.core.audit.anno;


import com.easy.core.audit.CommonGenerator;
import com.easy.core.audit.DefaultCommonGenerator;
import com.easy.core.audit.TimeType;
import com.easy.core.util.StringPool;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 生成时间
 *
 * @author liuph
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface CreateTime {

    /**
     * 时间生成类型，参见 {@link TimeType}
     *
     * @return 时间生成类型
     */
    TimeType type() default TimeType.SERVER;

    /**
     * <pre>
     *     日期格式化，不同类型处理如下
     *      {@link java.util.Date }: 此值不必填写；
     *      {@link LocalDateTime }: 此值不必填写；
     *      {@link LocalDate }: 此值不必填写；
     *      {@link Long   }: 此值默认为时间戳、若指定格式，则按格式化内容存储
     *      {@link String }: 此值默认格式为【yyyy-MM-dd HH:mm:ss】、若指定格式，则按格式化内容存储
     * </pre>
     *
     * @return 日期格式化
     */
    String format() default StringPool.EMPTY;


    /**
     * 日期生成器
     *
     * @return 主键生成器
     */
    Class<? extends CommonGenerator> generator() default DefaultCommonGenerator.class;
}
