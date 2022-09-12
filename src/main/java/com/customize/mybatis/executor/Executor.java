package com.customize.mybatis.executor;

import com.customize.mybatis.configuration.MappedStatement;

import java.util.List;

public interface Executor {
    <E> List<E> query(MappedStatement mappedStatement, Object [] parameter);
}
