package com.SpringforNew;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUser {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("Bean.xml");
        //根据id 获取bean
        User user = (User)context.getBean("user");
        System.out.println("id获取："+user);

        //根据类型 获取bean
        User user1 = context.getBean(User.class);
        System.out.println("类型获取："+user1);

        //根据id和类型 获取bean
        User user2 = context.getBean("user", User.class);
        System.out.println("id和类型获取："+user2);

    }
}
