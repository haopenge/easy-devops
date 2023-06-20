package com.youlai.system.common.annotation;

import java.lang.annotation.*;

/**
 * MP数据权限注解
 * <p>
 * https://gitee.com/baomidou/mybatis-plus/issues/I37I90
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @since 2021-12-10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DataPermission {

    /**
     * 数据权限 {@link com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor}
     */
    String deptAlias() default "";

    String deptIdColumnName() default "dept_id";

    String userAlias() default "";

    String userIdColumnName() default "create_by";

}

