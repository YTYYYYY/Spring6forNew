package com.SpringforNew.bi;

import com.sun.source.tree.LambdaExpressionTree;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Book {
    private String name;
    private String author;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }


    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean-bi.xml");
//        Book book = context.getBean(Book.class);
        Book book = context.getBean("bookAn", Book.class);
        System.out.println(book.toString());
    }
}
