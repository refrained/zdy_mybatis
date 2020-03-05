package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException, ClassNotFoundException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> selectList = rootElement.selectNodes("//select");
        Elementforeach(selectList,namespace);
        List<Element> insertList = rootElement.selectNodes("//insert");
        Elementforeach(insertList,namespace);
        List<Element> updateList = rootElement.selectNodes("//update");
        Elementforeach(updateList,namespace);
        List<Element> deleteList = rootElement.selectNodes("//delete");
        Elementforeach(deleteList,namespace);
    }

    public void Elementforeach(List<Element> list, String namespace){
        for (Element element : list) {
            String id = element.attributeValue("id");
            String paramterType = element.attributeValue("paramterType");
            String resultType = element.attributeValue("resultType");

            //statementId
            String key = namespace +"."+id;
            //sql语句
            String textTrim = element.getTextTrim();

            //封装MappedStatement
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParamterType(paramterType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(textTrim);

            //填充configuration
            configuration.getMappedStatementMap().put(key,mappedStatement);
        }
    }

    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        Class<?> aClass = Class.forName(paramterType);
        return aClass;
    }
}
