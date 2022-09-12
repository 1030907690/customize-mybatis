package com.customize.mybatis.sqlsession;

import java.util.List;

public interface SqlSession {

    <T> T selectOne(String statement, Object[] parameter);

    <T> List<T> selectList(String statement, Object[] parameter);

    <T> T getMapper(Class<T> type);

}
