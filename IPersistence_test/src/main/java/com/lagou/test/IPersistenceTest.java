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
        user.setUsername("lucy");
        user.setBirthday("2019-12-12");
        user.setPassword("123");
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
        user1.setUsername("张三");
        user1.setPassword("123");
        user1.setBirthday("1989-09-29");
        int i = iUserDao.insertData(user1);
        if(i==1){
            System.out.println("-----插入成功-----"+i);
        }else {
            System.out.println("-----插入失败-----"+i);
        }

    }

    @Test //更新
    public void test2() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);

        User user1 = new User();
        user1.setId(5);
        user1.setUsername("Tom");
        user1.setPassword("456");
        int i = iUserDao.updateData(user1);
        if(i==1){
            System.out.println("-----更新成功-----"+i);
        }else{
            System.out.println("-----更新失败-----"+i);
        }

    }

    @Test  //删除
    public void test3() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);

        User user1 = new User();
        user1.setId(5);
        //user1.setUsername("Tom");
        int i = iUserDao.deleteData(user1);
        if(i==1){
            System.out.println("-----删除成功-----"+i);
        }else{
            System.out.println("-----删除失败-----"+i);
        }

    }

}
