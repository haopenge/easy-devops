package com.easy.api.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author liuph
 */
public class ObjectUtil {

    /**
     * 对象转化（数组）
     *
     * @param sources     源数据
     * @param targetClass 目标数据class
     * @param <T>         目标数据class
     * @return 目标数据对象（数组）
     */
    public static <T> List<T> convertList(List<?> sources, Class<T> targetClass) {
        if (sources == null || sources.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> result = new ArrayList<>();
        for (Object source : sources) {
            T instance = ClassUtil.newInstance(targetClass);
            assert instance != null;
            BeanUtils.copyProperties(source, instance);
            result.add(instance);
        }
        return result;
    }

    /**
     * 对象转化
     *
     * @param source      源数据
     * @param targetClass 目标数据class
     * @param <T>         目标数据class
     * @return 目标数据对象
     */
    public static <T> T convert(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        T instance = ClassUtil.newInstance(targetClass);
        assert instance != null;
        BeanUtils.copyProperties(source, instance);
        return instance;
    }
}
