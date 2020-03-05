package com.lagou.config;

import com.lagou.io.Resources;
import com.lagou.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigerBuilder {
    Configuration configuration;
    public XMLConfigerBuilder(){

        this.configuration = new Configuration();
    }

    /**
     * 改方法就是使用dom4j对配置文件进行封装，封装Configuration
     */
    public Configuration parseConfiguration(InputStream inputStream) throws DocumentException, PropertyVetoException, ClassNotFoundException {
        Document document = new SAXReader().read(inputStream);

        //获取到<configuration>根对象
        Element rootElement = document.getRootElement();
        List<Element> elementList = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element element : elementList) {
            String name=element.attributeValue("name");
            String value=element.attributeValue("value");
            properties.setProperty(name,value);
        }
        //连接池
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));

        //填充configuration
        configuration.setDataSource(comboPooledDataSource);

        //mapper.xml部分
        List<Element> mapperElements = rootElement.selectNodes("//mapper");

        for (Element mapperElement : mapperElements) {
            String mapperPath = mapperElement.attributeValue("resource");
            InputStream resourceAsStream = Resources.getResourceAsStream(mapperPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsStream);
        }

        return configuration;
    }
}
