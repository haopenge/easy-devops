package com.easy.core.audit;

/**
 * 通用生成器定义
 *
 * @author liuph
 */
public interface CommonGenerator<T> {

    /**
     * 通用属性值生成方法
     *
     * @param entity    实体对象
     * @param annoField 待注入属性封装类
     * @param isCreate  是否为新增
     * @return 属性值
     */
    T generate(Object entity, AnnoField annoField, boolean isCreate);

}
