package com.SpringforNew.jdbctest;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class druidTest {

    @Test
    public void te2(){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean-jdbc.xml");
        DruidDataSource dataSource = context.getBean(DruidDataSource.class);
        System.out.println(dataSource.getUrl());
    }

    @Test
    public void te(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/Demodb?serverTimezero=GMT%2b8");
        dataSource.setUsername("");
        dataSource.setPassword("");
        dataSource.setDriverClassName("");
        System.out.println(dataSource.getUrl());
    }
}
