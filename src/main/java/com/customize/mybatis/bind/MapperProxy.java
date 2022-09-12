package com.customize.mybatis.bind;

import com.customize.mybatis.configuration.Configuration;
import com.customize.mybatis.configuration.MappedStatement;
import com.customize.mybatis.mapping.SqlCommandType;
import com.customize.mybatis.sqlsession.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

public class MapperProxy implements InvocationHandler {

    private final SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        Configuration configuration = sqlSession.getConfiguration();
        MappedStatement mappedStatement = configuration.getMappedStatement().get(method.getDeclaringClass().getName() + "." + method.getName());
        switch (mappedStatement.getSqlCommandType()) {
            case SELECT:
                if (isReturnsMany(method)) {
                    return sqlSession.selectList(method.getDeclaringClass().getName() + "." + method.getName(), args);
                } else {
                    return sqlSession.selectOne(method.getDeclaringClass().getName() + "." + method.getName(), args);
                }
            case INSERT:
                break;
//                ...省略...
            default:
                break;

        }
        return null;
    }

    private boolean isReturnsMany(Method method) {
        return Collection.class.isAssignableFrom(method.getReturnType());
    }
}
