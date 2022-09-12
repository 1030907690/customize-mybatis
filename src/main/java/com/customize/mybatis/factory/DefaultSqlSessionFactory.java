package com.customize.mybatis.factory;

import com.customize.mybatis.configuration.Configuration;
import com.customize.mybatis.configuration.MappedStatement;
import com.customize.mybatis.sqlsession.DefaultSqlSession;
import com.customize.mybatis.sqlsession.SqlSession;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DefaultSqlSessionFactory implements SqlSessionFactory {


    private final Configuration configuration = new Configuration();

    private final String MAPPER_CONFIG_LOCATION = "mappers";

    private final String DB_CONFIG_FILE = "mybatis.properties";

    public DefaultSqlSessionFactory() {
        loadDbInfo();
        loadMapperInfo();
    }

    private void loadMapperInfo() {
        URL resource = this.getClass().getClassLoader().getResource(MAPPER_CONFIG_LOCATION);
        File mappersFolder = new File(resource.getFile());
        File[] files = mappersFolder.listFiles();
        for (File file : files) {
            loadMapperInfo(file);
        }

    }

    private void loadMapperInfo(File file) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if (null == document) {
            throw new RuntimeException("解析失败 " + file.getPath());
        }

        Element element = document.getRootElement();
        String namespace = element.attribute("namespace").getValue();

        String [] tags = {"select","insert","update","delete"};
        List<Element> all = new ArrayList<>();
        for (String tag : tags) {
            List<Element> elements = element.elements(tag);
            all.addAll(elements);
        }

        for (Element ele : all) {
            MappedStatement mappedStatement = new MappedStatement();
            String id = ele.attribute("id").getData().toString();
            String resultType = ele.attribute("resultType").getData().toString();
            String sql = ele.getData().toString();

            mappedStatement.setId(namespace + "."+id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);
            configuration.getMappedStatement().put(mappedStatement.getNamespace(),mappedStatement);

        }
    }

    private void loadDbInfo() {
        InputStream dbStream = this.getClass().getClassLoader().getResourceAsStream(DB_CONFIG_FILE);
        Properties p = new Properties();
        try {
            p.load(dbStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dbStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setJdbcPropertyValues(p);

    }

    private void setJdbcPropertyValues(Properties p) {
        configuration.setJdbcDriver(p.getProperty("driver"));
        configuration.setJdbcUrl(p.getProperty("url"));
        configuration.setJdbcName(p.getProperty("username"));
        configuration.setJdbcPassword(p.getProperty("password"));
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
