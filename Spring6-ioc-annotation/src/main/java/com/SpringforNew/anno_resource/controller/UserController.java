package com.SpringforNew.anno_resource.controller;

import com.SpringforNew.anno_resource.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Resource(name = "myUserService")
    private UserService userService;

    public void addUser(){
        System.out.println("UserController...");
        userService.addUser();
    }

    @Test
    public void te(){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean.xml");
        UserController user = context.getBean(UserController.class);
        user.addUser();
    }
}
