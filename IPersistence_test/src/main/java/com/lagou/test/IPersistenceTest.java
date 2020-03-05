package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {
    @Test //查询
    public void test() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用sqlSession封装的方法
//        User user = new User();
//        user.setId(1);
//        user.setUsername("老王");
//        User user1 = sqlSession.selectOne("user.selectOne", user);
//        System.out.println(user1);

        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);

        List<User> all = iUserDao.findAll();
        System.out.println("-----查询所有结果-----");
        for (User user : all) {
            System.out.println(user);
        }
        //查询单个
        User user = new User();
        user.setId(1);
        user.setUsername("老王");
        User byCondition = iUserDao.findByCondition(user);
        System.out.println("-----查询单个结果------1");
        System.out.println(byCondition);
    }

    @Test //新增
    public void test1() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);

        User user1 = new User();
        user1.setId(5);
        user1.setUsername("lucy");
        iUserDao.insertData(user1);
        System.out.println("-----插入成功-----");
    }

    @Test //更新
    public void test2() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);

        User user1 = new User();
        user1.setId(4);
        user1.setUsername("Tom");
        iUserDao.updateData(user1);
        System.out.println("-----更新成功-----");
    }

    @Test  //删除
    public void test3() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);

        User user1 = new User();
        user1.setId(4);
        user1.setUsername("lucy");
        iUserDao.deleteData(user1);
        System.out.println("-----删除成功-----");
    }

}
