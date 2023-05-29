package com.easy.api.config;

import com.easy.core.audit.CommonAuditInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.util.List;
import java.util.Properties;

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

        if(args.length > 2 || obj instanceof List){
            return invocation.proceed();
        }

        SqlCommandType sqlCommandType = ms.getSqlCommandType();
        switch (sqlCommandType) {
            case INSERT:
                CommonAuditInterceptor.handler(obj, true);
                break;
            case UPDATE:
                CommonAuditInterceptor.handler(obj, false);
                break;
            default:
                break;
        }
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
