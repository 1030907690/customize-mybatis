package com.customize.mybatis;

import com.customize.mybatis.entity.User;
import com.customize.mybatis.factory.DefaultSqlSessionFactory;
import com.customize.mybatis.factory.SqlSessionFactory;
import com.customize.mybatis.mapper.UserMapper;
import com.customize.mybatis.sqlsession.SqlSession;

public class TestMybatis {
    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectById(1);
        System.out.println(user.getUserName());
    }
}
