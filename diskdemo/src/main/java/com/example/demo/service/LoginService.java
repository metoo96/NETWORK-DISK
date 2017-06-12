package com.example.demo.service;

import com.example.demo.dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lenovo on 2017/6/11.
 */
@Service
public class LoginService {
    @Autowired
    LoginDao logindao;
    public boolean addUser(String username,String password){
        if(logindao.addUser(username,password)){
            return true;
        }else{
            return false;
        }
    }
    public String find_user(String username){
        return logindao.find_user(username);
    }
    public String find_password(String username){
        return logindao.find_password(username);
    }
}
