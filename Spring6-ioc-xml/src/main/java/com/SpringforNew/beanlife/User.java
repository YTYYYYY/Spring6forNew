package com.SpringforNew.beanlife;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class User {

    private String name;

    public void initMethod(){ System.out.println("4.对象初始化"); }

    public void destroyMethod(){ System.out.println("7.对象销毁"); }

    public User() { System.out.println("1.无参构造"); }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("2.设置属性");
        this.name = name;
    }

    @Test
    public void te(){
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("bean-life.xml");
        User user = context.getBean(User.class);
        System.out.println("6.对象就绪:"+user);
        context.close();
    }
}
