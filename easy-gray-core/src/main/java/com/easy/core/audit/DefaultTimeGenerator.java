package com.easy.core.audit;

import cn.hutool.core.text.CharSequenceUtil;
import com.easy.core.audit.anno.CreateTime;
import com.easy.core.mapper.TimeMapper;
import com.easy.core.util.ClassUtil;
import com.easy.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;


/**
 * 默认时间生成器
 *
 * @author liuph
 */
@Service
public class DefaultTimeGenerator implements CommonGenerator<Object> {

    /**
     * 日期格式化注解属性
     */
    public static final String DATE_FORMAT_KEY = "format";

    /**
     * 日期生成类型
     */
    public static final String DATE_CREATE_TYPE = "type";

    /**
     * 默认日志格式化类型
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private TimeMapper timeMapper;

    @Override
    public Object generate(Object entity, AnnoField annoField, boolean isCreate) {
        Annotation annotation = annoField.getAnnotation();
        if (Objects.equals(annotation.annotationType(), CreateTime.class)) {
            return isCreate ? getDatetime(annoField) : null;
        }
        return getDatetime(annoField);
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    private Object getDatetime(AnnoField annoField) {
        Annotation annotation = annoField.getAnnotation();
        Field field = annoField.getField();
        String format = (String) ClassUtil.getFieldAnnotationMemberValue(annotation, DATE_FORMAT_KEY);
        TimeType type = (TimeType) ClassUtil.getFieldAnnotationMemberValue(annotation, DATE_CREATE_TYPE);

        // Date
        Date nowTime = new Date();
        if (Objects.equals(type, TimeType.DB)) {
            nowTime = timeMapper.getNowTime();
        }

        Class<?> fieldType = field.getType();

        if (Objects.equals(fieldType, Date.class)) {
            return nowTime;
        }

        // LocalDateTime
        if (Objects.equals(fieldType, LocalDateTime.class)) {
            return DateUtil.dateToLocalDateTime(nowTime);
        }

        // LocalDate
        if (Objects.equals(fieldType, LocalDate.class)) {
            return DateUtil.dateToLocalDate(nowTime);
        }

        // Long
        if (Objects.equals(fieldType, Long.class)) {
            if (CharSequenceUtil.isBlank(format)) {
                return nowTime.getTime();
            }
            String dateStr = getStringDate(format, nowTime);
            return Long.parseLong(dateStr);
        }

        // String
        if (CharSequenceUtil.isBlank(format)) {
            return getStringDate(DEFAULT_DATE_FORMAT, nowTime);
        }
        return getStringDate(format, nowTime);
    }

    /**
     * 格式化日期
     *
     * @param format  格式
     * @param nowTime 日期
     * @return 格式化日期
     */
    private static String getStringDate(String format, Date nowTime) {
        return DateUtil.fastDateFormat(nowTime, format);
    }
}
