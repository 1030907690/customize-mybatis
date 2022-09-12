package com.customize.mybatis.executor;

import com.customize.mybatis.configuration.Configuration;
import com.customize.mybatis.configuration.MappedStatement;
import com.customize.mybatis.util.ReflectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {
    private final Configuration configuration;

    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> query(MappedStatement mappedStatement, Object parameter) {
        List<E> ret = new ArrayList<>();
        try {
            Class.forName(configuration.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(configuration.getJdbcUrl(), configuration.getJdbcName(), configuration.getJdbcPassword());
            String regex = "#\\{([^}])*\\}";
            String sql = mappedStatement.getSql().replaceAll(regex, "?");
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            setParameter(preparedStatement, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            handlerResult(resultSet, ret, mappedStatement.getResultType());
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private <E> void handlerResult(ResultSet resultSet, List<E> ret, String resultType) {

        Class<?> resClz = null;
        try {
            resClz = Class.forName(resultType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            while (resultSet.next()) {
                Object o = resClz.newInstance();
                ReflectionUtil.setPropToBeanFromResultSet(o, resultSet);
                ret.add((E) o);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setParameter(PreparedStatement preparedStatement, Object parameter) {
        if (parameter instanceof Integer) {
            try {
                preparedStatement.setInt(1, ((Integer) parameter).intValue());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //...省略其他类型...
    }
}
