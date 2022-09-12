package com.customize.mybatis.sqlsession;

import com.customize.mybatis.configuration.Configuration;
import com.customize.mybatis.configuration.MappedStatement;
import com.customize.mybatis.executor.Executor;
import com.customize.mybatis.executor.SimpleExecutor;
import com.customize.mybatis.bind.MapperProxy;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Proxy;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;

    private Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        executor = new SimpleExecutor(configuration);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        List<T> list = this.selectList(statement, parameter);
        if (!CollectionUtils.isEmpty(list)) {
            if (list.size() > 1) {
                throw new RuntimeException("too many result");
            } else {
                return list.get(0);
            }
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statement, Object parameter) {
        MappedStatement mappedStatement = configuration.getMappedStatement().get(statement);
        return executor.query(mappedStatement, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        MapperProxy mapperProxy = new MapperProxy(this);
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, mapperProxy);
    }
}
