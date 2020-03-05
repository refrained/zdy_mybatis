package com.lagou.sqlSession;

import java.util.List;

public interface SqlSession {
    //查询所有
    public <E> List<E> selectList(String statementid, Object... params) throws Exception;

    //根据条件查询单个
    public <T> T selectOne(String statementid, Object... params) throws Exception;

    //插入方法
    public int insertData(String statementid, Object... params) throws Exception;

    //更新方法
    public int updateData(String statementid, Object... params) throws Exception;

    //删除方法
    public int deleteData(String statementid, Object... params) throws Exception;

    //为Dao接口生成代理实现类
    public <T> T getMapper(Class<?> mapperClass);
}
