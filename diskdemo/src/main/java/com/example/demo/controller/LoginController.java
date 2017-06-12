package com.example.demo.controller;

import com.example.demo.service.LoginService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Lenovo on 2017/6/11.
 */
@Controller
public class LoginController {
    @Autowired
    LoginService loginservice;
    /**
     *登录
     */
    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String login(){
        return "login";
    }
    @RequestMapping(value="/login",method=RequestMethod.POST)
    public String logining(@Param("username")String username, @Param("password")String password, HttpServletResponse response){
        String isExisted=loginservice.find_password(username);
        if(isExisted==null||("".equals(isExisted.trim()))){
            return "false";
        }else{
            if(isExisted.equals(password)) {
                Cookie cookie=new Cookie("userid",username);
                cookie.setMaxAge(3600*24);
                response.addCookie(cookie);
                return "redirect:/File/index";
            }
            return "false";
        }
    }
    /**
     * 注册
     */
    @RequestMapping(value="/register",method=RequestMethod.GET)
    public String register(){
        return "register";
    }
    @RequestMapping(value="/register",method=RequestMethod.POST)
    public String registering(@RequestParam("username")String username, @RequestParam("password")String password){
        String user=loginservice.find_user(username);
        if(user==null||("".equals(user.trim()))){
            loginservice.addUser(username,password);
            return "true";
        }else{
            return "registerfalse";
        }
    }
}
