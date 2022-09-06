package com.customize.mybatis.factory;

import com.customize.mybatis.sqlsession.SqlSession;

public interface SqlSessionFactory {
    SqlSession openSession();
}
