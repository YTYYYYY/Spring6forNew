package com.SpringforNew.jdbc_tx.controller;

import com.SpringforNew.jdbc_tx.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    //买书
    public void buyBook(Integer bookId,Integer userId){
        bookService.buyBook(bookId,userId);
    }
}
