package com.customize.mybatis.configuration;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private String jdbcDriver;


    private String jdbcUrl;

    private String jdbcPassword;

    private String jdbcName;

    private Map<String,MappedStatement> mappedStatement = new HashMap<>();
    public Configuration() {
    }

    public Configuration(String jdbcDriver, String jdbcUrl, String jdbcPassword, String jdbcName) {
        this.jdbcDriver = jdbcDriver;
        this.jdbcUrl = jdbcUrl;
        this.jdbcPassword = jdbcPassword;
        this.jdbcName = jdbcName;
    }

    public Map<String, MappedStatement> getMappedStatement() {
        return mappedStatement;
    }

    public void setMappedStatement(Map<String, MappedStatement> mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public String getJdbcName() {
        return jdbcName;
    }

    public void setJdbcName(String jdbcName) {
        this.jdbcName = jdbcName;
    }
}
