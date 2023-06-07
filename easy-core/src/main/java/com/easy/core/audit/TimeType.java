package com.easy.core.audit;

/**
 * 时间类型
 *
 * @author liuph
 */
public enum TimeType {
    /**
     * 数据库时间，通过select now() 获取
     */
    DB,

    /**
     * 服务器时间，通过new Date() 获取
     */
    SERVER;
}
