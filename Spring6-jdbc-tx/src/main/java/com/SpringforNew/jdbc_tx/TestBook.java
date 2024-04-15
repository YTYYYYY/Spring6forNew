package com.SpringforNew.jdbc_tx;

import com.SpringforNew.jdbc_tx.config.SpringConfig;
import com.SpringforNew.jdbc_tx.controller.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = "classpath:bean-jdbc-tx.xml")
public class TestBook {

    @Autowired
    private BookController bookController;

    @Test
    public void te1(){
        System.out.println(bookController);
        //bookController.buyBook(1,1);
    }

    @Test
    public void te2(){
        ApplicationContext context =
                new AnnotationConfigApplicationContext(SpringConfig.class);
        BookController bookCon = context.getBean(BookController.class);
        bookCon.buyBook(1,1);
    }
}
