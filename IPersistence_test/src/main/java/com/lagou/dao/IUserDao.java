package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserDao {
    //查询所有
    public List<User> findAll();

    //查询单个
    public User findByCondition(User user);

    //新增
    public int insertData(User user);

    //修改
    public int updateData(User user);

    //删除
    public int deleteData(User user);
}
