package com.liuao.game_card_sell.filter.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import java.time.LocalDateTime;
import java.util.Properties;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class CreateTimeInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        // 只处理 INSERT 语句
        if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
            Object parameter = invocation.getArgs()[1];
            // 反射设置 createTime 字段
            if (parameter != null) {
                setCreateTimeIfNull(parameter);
            }
        }
        return invocation.proceed();
    }

    private void setCreateTimeIfNull(Object obj) {
        try {
            java.lang.reflect.Field field = obj.getClass().getDeclaredField("createTime");
            field.setAccessible(true);
            if (field.get(obj) == null) {
                field.set(obj, LocalDateTime.now());
            }
        } catch (Exception e) {
            // 忽略异常，可能不是 User 类
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 可配置参数
    }
}