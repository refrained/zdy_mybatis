package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserDao {
    //查询所有
    public List<User> findAll();

    //查询单个
    public User findByCondition(User user);

    //新增
    public void insertData(User user);

    //修改
    public void updateData(User user);

    //删除
    public void deleteData(User user);
}
