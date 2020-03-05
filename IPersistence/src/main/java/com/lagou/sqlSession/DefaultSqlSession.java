package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSqlSession implements SqlSession{
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementid, Object... params) throws Exception {
        //完成对SimpleExecutor中query方法的调用
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        List<Object> list = new SimpleExecutor().query(configuration, mappedStatement, params);
        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementid, Object... params) throws Exception {
        List<Object> objects = selectList(statementid,params);
        if(objects.size()==1){
            return (T) objects.get(0);
        }else {
            throw new RuntimeException("返回结果为空或结果不唯一");
        }
    }

    @Override
    public int insertData(String statementid, Object... params) throws Exception {
        //完成对SimpleExecutor中insert方法的调用
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        int i = simpleExecutor.insert(configuration,mappedStatement,params);
        return i;
    }

    @Override
    public int updateData(String statementid, Object... params) throws Exception {
        //完成对SimpleExecutor中update方法的调用
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        int i = simpleExecutor.update(configuration,mappedStatement,params);
        return i;
    }

    @Override
    public int deleteData(String statementid, Object... params) throws Exception {
        //完成对SimpleExecutor中delete方法的调用
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        int i = simpleExecutor.delete(configuration,mappedStatement,params);
        return i;
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        // 使用JDK动态代理来为Dao接口生成代理对象，并返回

        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 底层都还是去执行JDBC代码 //根据不同情况，来调用selctList或者selectOne
                // 准备参数 1：statmentid :sql语句的唯一标识：namespace.id= 接口全限定名.方法名
                // 方法名：findAll
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();

                String statementId = className+"."+methodName;

                // 准备参数2：params:args
                // 获取被调用方法的返回值类型
                Type genericReturnType = method.getGenericReturnType();
                // 判断是否进行了 泛型类型参数化
                if(genericReturnType instanceof ParameterizedType){
                    List<Object> objects = selectList(statementId, args);
                    return objects;
                }
                MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
                String sql = mappedStatement.getSql();
                if(sql.startsWith("insert")||sql.startsWith("INSERT")){
                    return insertData(statementId,args);
                }
                if(sql.startsWith("update")||sql.startsWith("UPDATE")){
                    return updateData(statementId,args);
                }
                if(sql.startsWith("delete")||sql.startsWith("DELETE")){
                    return deleteData(statementId,args);
                }

                return selectOne(statementId,args);

            }
        });

        return (T) proxyInstance;
    }

}
