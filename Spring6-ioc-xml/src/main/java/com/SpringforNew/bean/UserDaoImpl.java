package com.SpringforNew.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDaoImpl implements UserDao {
    @Override
    public void run() {
        System.out.println("dao run..");
    }

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("Bean.xml");
        UserDao userDao = context.getBean(UserDao.class);
        System.out.println(userDao);
        userDao.run();
    }
}
