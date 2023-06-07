package com.easy.devops.api.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author liuph
 */
@Slf4j
public class ClassUtil {

    public static final String TYPE = "type";

    public static final String MEMBER_VALUES = "memberValues";

    /**
     * Object Class
     */
    private static final Class<?> OBJECT_CLASS = Object.class;

    private ClassUtil() {
    }

    /**
     * 获取给定类的包含指定注解类与对应字段列表的关联映射.
     *
     * @param clazz       待获取字段所属类
     * @param annoClasses 待获取映射的字段注解类列表
     * @return 获取到的给定类中注解类与对应字段列表关联映射
     */
    public static Map<Class<? extends Annotation>, List<Field>> getAnnoFieldsMap(Class<?> clazz, List<Class<? extends Annotation>> annoClasses) {
        if (CollectionUtils.isEmpty(annoClasses)) {
            return Collections.emptyMap();
        }
        List<Field> fields = getFields(clazz, true);
        Map<Class<? extends Annotation>, List<Field>> annoFieldsMap = new HashMap<>(annoClasses.size());
        fields.forEach(field -> {
            for (Class<? extends Annotation> annoClass : annoClasses) {
                if (hasAnnotationType(field, annoClass)) {
                    annoFieldsMap.computeIfAbsent(annoClass, k -> new ArrayList<>()).add(field);
                    break;
                }
            }
        });
        return annoFieldsMap;
    }

    /**
     * 创建类实例
     *
     * @param clazz 类
     * @param <T>   类class
     * @return 类实例
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.warn("newInstance error , clazz : {}", clazz.getName());
            return null;
        }
    }

    /**
     * 设置属性值
     *
     * @param fieldName 属性名
     * @param obj       对象实例
     * @param value     属性值
     * @return true-设置成功 false-设置失败
     */
    public static boolean setFiledValue(String fieldName, Object obj, Object value) {
        Field field = getField(fieldName, obj.getClass());
        if (Objects.isNull(field)) {
            return false;
        }
        field.setAccessible(true);
        try {
            field.set(obj, value);
            return true;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    /**
     * 设置属性值
     */
    public static boolean setFiledValue(Field field, Object obj, Object value) {
        if (Objects.isNull(field) || Objects.isNull(obj)) {
            return false;
        }
        field.setAccessible(true);
        try {
            field.set(obj, value);
            return true;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    /**
     * 判断属性值是否存在
     */
    public static <T> Field existColumn(T model, String fieldName) {
        Field field = getField(fieldName, model.getClass());
        return Objects.nonNull(field) ? field : null;
    }

    /**
     * 获取属性
     */
    public static Field getField(String fieldName, Class<?> clazz) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                return getField(fieldName, clazz.getSuperclass());
            }
        }
        return null;
    }

    /**
     * 获取属性值
     */
    public static Object getFieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取属性值
     *
     * @param obj       目标对象
     * @param fieldName 目标对象属性名
     * @return 目标对象属性值
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取属性属性的属性值
     *
     * @param annotation 注解
     * @return 注解类型
     */
    public static Object getFieldAnnotationMemberValue(Annotation annotation, String name) {
        Map<String, Object> annoMemberValues = getFieldAnnotationMemberValues(annotation);
        if (annoMemberValues == null) {
            return null;
        }
        return annoMemberValues.get(name);
    }

    /**
     * 获取注解类型
     *
     * @param annotation 注解
     * @return 注解类型
     */
    public static Map<String, Object> getFieldAnnotationMemberValues(Annotation annotation) {
        InvocationHandler handler = Proxy.getInvocationHandler(annotation);
        Field field = null;
        try {
            field = handler.getClass().getDeclaredField(MEMBER_VALUES);
        } catch (NoSuchFieldException e) {
            return null;
        }
        // sonar检测时手动跳过检测
        field.setAccessible(true);
        // 获取 注解类型
        try {
            return (Map<String, Object>) field.get(handler);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * 获取注解类型
     *
     * @param annotation 注解
     * @return 注解类型
     */
    public static Class<?> getFieldAnnotationType(Annotation annotation) {
        InvocationHandler handler = Proxy.getInvocationHandler(annotation);
        Field field = null;
        try {
            field = handler.getClass().getDeclaredField(TYPE);
        } catch (NoSuchFieldException e) {
            return null;
        }
        // sonar检测时手动跳过检测
        field.setAccessible(true);
        // 获取 注解类型
        try {
            return (Class<?>) field.get(handler);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * 获取当前类定义的注解元信息map
     *
     * @param clazz     目标类
     * @param annoClass 自定义spring bean注解类
     * @param <A>       Annotation 的子类
     * @return 注解元信息map
     */
    public static <A extends Annotation> Map<String, Object> getClassAnnoMemberValues(Class<?> clazz, Class<A> annoClass) {
        A anno = clazz.getAnnotation(annoClass);
        if (Objects.nonNull(anno)) {
            InvocationHandler handler = Proxy.getInvocationHandler(anno);
            Field field = null;
            try {
                field = handler.getClass().getDeclaredField(MEMBER_VALUES);
            } catch (NoSuchFieldException e) {
                return Collections.emptyMap();
            }
            // sonar检测时手动跳过检测
            field.setAccessible(true);
            // 获取 memberValues (目标注解的信息都在 memberValues 中)
            try {
                return (Map<String, Object>) field.get(handler);
            } catch (IllegalAccessException e) {
                return Collections.emptyMap();
            }
        }
        return Collections.emptyMap();
    }

    /**
     * 获取当前类定义的注解元信息
     *
     * @param clazz         目标类
     * @param annoClass     自定义spring bean注解类
     * @param <A>           Annotation 的子类
     * @param annoFieldName 注解属性名
     * @return 注解元信息map
     */
    public static <A extends Annotation> Object getClassAnnoMemberValue(Class<?> clazz, Class<A> annoClass, String annoFieldName) {
        Map<String, Object> annoMemberValues = getClassAnnoMemberValues(clazz, annoClass);
        if (annoMemberValues == null) {
            return null;
        }
        return annoMemberValues.get(annoFieldName);
    }

    /**
     * 判断是否属于当前类的实例
     *
     * @param clazz          类
     * @param interfaceClass 接口
     * @return true-是；false-否
     */
    public static boolean isImplement(Class<?> clazz, Class<?> interfaceClass) {
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length == 0) {
            return false;
        }
        return Arrays.asList(interfaces).contains(interfaceClass);
    }

    /**
     * 安全的根据类名称加载并实例化类对象.
     *
     * @param aClass 待加载和实例化的类
     * @param <T>    类对象类型
     * @return 实例化的类对象
     */
    public static <T> T safeNewInstance(Class<T> aClass) {
        try {
            if (aClass != null) {
                return aClass.newInstance();
            }
        } catch (Throwable e) {
            ;
        }
        return null;
    }

    /**
     * 获取指定类及各级父类的字段列表.
     * <p>字段包含public、protected、default、private级别.</p>
     *
     * @param clazz  待获取字段列表的类
     * @param scanUp 是否迭代获取父类字段
     * @return 获取到的类字段列表
     */
    private static List<Field> getFields(Class clazz, boolean scanUp) {
        List<Field> fields = new ArrayList<Field>();
        try {
            Class theClass = clazz;
            synchronized (theClass) {
                do {
                    fields.addAll(Arrays.asList(theClass.getDeclaredFields()));
                    theClass = theClass.getSuperclass();
                } while (scanUp && theClass != OBJECT_CLASS);
            }
        } catch (SecurityException e) {
            ;
        }
        return fields;
    }

    /**
     * 判定给定Field是否存在指定类型注解.
     *
     * @param field     待判定的Field字段
     * @param annoClass 待判定是否存在的注解类
     * @return 判定结果（true-存在、false-不存在）
     */
    private static boolean hasAnnotationType(Field field, Class<? extends Annotation> annoClass) {
        return (field != null) && (field.getAnnotation(annoClass) != null);
    }
}
