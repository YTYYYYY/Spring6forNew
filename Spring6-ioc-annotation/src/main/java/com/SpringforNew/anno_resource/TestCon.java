package com.SpringforNew.anno_resource;

import com.SpringforNew.anno_resource.config.SpringConfig;
import com.SpringforNew.anno_resource.controller.UserController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestCon {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(SpringConfig.class);
        UserController userController = context.getBean(UserController.class);
        userController.addUser();
    }
}
