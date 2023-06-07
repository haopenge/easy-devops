package com.easy.devops.api.config;

import com.easy.core.audit.CommonAuditInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

/**
 * 审计拦截器
 *
 * @author liuph
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class AuditInterceptor implements Interceptor {
    /**
     * intercept:拦截
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object obj = args[1];

        // 多参数、批量操作处理
        Set<Object> objects = new HashSet<>();
        if (obj instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) obj;
            map.forEach((loopKey, loopValue) -> {
                if (loopValue instanceof Map) {
                    Map<?, ?> innnerMap = (Map<?, ?>) loopValue;
                    objects.addAll(innnerMap.values());
                }else if (loopValue instanceof Collection) {
                    Collection<?> innerCollection = (Collection<?>) loopValue;
                    objects.addAll(innerCollection);
                }else {
                    objects.add(loopValue);
                }
            });
        } else {
            objects.add(obj);
        }

        SqlCommandType sqlCommandType = ms.getSqlCommandType();
        boolean isCreated = Objects.equals(sqlCommandType,SqlCommandType.INSERT);
        objects.forEach(loopObj -> CommonAuditInterceptor.handler(loopObj, isCreated));
        return invocation.proceed();
    }

    /**
     * 包装目标对象，为目标对象创建一个代理对象
     *
     * @param target 目标对象
     * @author liuph
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 拦截器自定义属性
     *
     * @author liuph
     * @date 2021/05/16 13:11
     */
    @Override
    public void setProperties(Properties properties) {
    }

}
