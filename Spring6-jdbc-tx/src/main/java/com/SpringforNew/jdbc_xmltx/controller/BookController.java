package com.SpringforNew.jdbc_xmltx.controller;

import com.SpringforNew.jdbc_xmltx.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    //买书
    public void buyBook(Integer bookId,Integer userId){
        bookService.buyBook(bookId,userId);
    }
}
