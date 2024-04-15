package com.SpringforNew.jdbc_xmltx;

import com.SpringforNew.jdbc_xmltx.controller.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = "classpath:bean-jdbc-xmltx.xml")
public class TestXmltx {
    @Autowired
    private BookController bookController;
    @Test
    public void te(){
        System.out.println(bookController);
        //bookController.buyBook(1,1);
    }
}
