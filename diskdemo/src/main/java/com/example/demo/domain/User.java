package com.example.demo.domain;

/**
 * Created by Lenovo on 2017/6/11.
 */
//用户实体类
public class User {
    private int id;//编号
    private String username;//用户名
    private String password;//密码

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
