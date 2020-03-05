package com.lagou.sqlSession;

import com.lagou.config.XMLConfigerBuilder;
import com.lagou.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    private Configuration configuration;

    public SqlSessionFactoryBuilder(){

        this.configuration = new Configuration();
    }

    public SqlSessionFactory build(InputStream inputStream) throws DocumentException, PropertyVetoException, ClassNotFoundException {
        //1.使用dom4j解析配置文件，封装Configuration
        XMLConfigerBuilder xmlConfigerBuilder = new XMLConfigerBuilder();
        Configuration configuration = xmlConfigerBuilder.parseConfiguration(inputStream);

        //2.创建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return sqlSessionFactory;
    }
}
