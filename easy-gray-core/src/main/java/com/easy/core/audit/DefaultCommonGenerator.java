package com.easy.core.audit;

/**
 * <pre>
 * 默认生成器，仅起占位作用
 * 若要覆写，手动实现 {@link CommonGenerator}
 * </pre>
 *
 * @author liuph
 */
public class DefaultCommonGenerator implements CommonGenerator<Object> {

    @Override
    public Object generate(Object entity, AnnoField annoField, boolean isCreate) {
        return null;
    }
}
