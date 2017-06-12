package com.example.demo.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by Lenovo on 2017/6/11.
 */
public interface LoginDao {
    public boolean addUser(@Param("username")String username, @Param("password")String password);
    public String find_user(@Param("username")String username);
    public String find_password(@Param("username")String username);
}