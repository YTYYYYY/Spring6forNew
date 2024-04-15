package com.SpringforNew.autoxml.controller;

import com.SpringforNew.autoxml.service.UserService;
import com.SpringforNew.autoxml.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserController {
    //UserService userService = new UserServiceImpl();
    private UserService userService;
    public void setUserService(UserService userService) {this.userService = userService;}
    public void addUser(){
        System.out.println("UserController..");
        userService.addUserService();
    }

    @Test
    public void te2(){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean-auto.xml");
        UserController userController = context.getBean("userController", UserController.class);
        userController.addUser();
    }

    @Test
    public void te1(){
        addUser();
    }
}
