package com.SpringforNew.jdbc_tx.dao;

import com.SpringforNew.jdbc_tx.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer getPriceById(Integer bookId) {
        return jdbcTemplate.queryForObject(
                "SELECT price FROM spring_jdbc_bookshop.book_info WHERE id=?",
                Integer.class, bookId);
    }

    @Override
    public Integer getNumById(Integer bookId) {
        return jdbcTemplate.queryForObject(
                "SELECT num FROM spring_jdbc_bookshop.book_info WHERE id=?",
                Integer.class,bookId);
    }

    @Override
    public void updateNum(Integer bookId, Integer updatedNum) {
        jdbcTemplate.update(
                "UPDATE spring_jdbc_bookshop.book_info SET num=? WHERE id=?",
                updatedNum,bookId);
    }

}
