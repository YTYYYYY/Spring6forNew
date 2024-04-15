package com.SpringforNew.myfactorybean;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class User {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Test
    public void te(){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean-factorybean.xml");
        User user = (User)context.getBean("user");
        System.out.println(user);
    }
}
