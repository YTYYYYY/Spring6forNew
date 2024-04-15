package com.SpringforNew.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Orders {

    @Test
    public void te(){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean-scope.xml");
        Orders orders = context.getBean(Orders.class);
        System.out.println(orders);
        Orders orders1 = context.getBean(Orders.class);
        System.out.println(orders1);
    }

}
