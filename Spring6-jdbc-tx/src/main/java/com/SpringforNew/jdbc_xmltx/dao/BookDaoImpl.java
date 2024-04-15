package com.SpringforNew.jdbc_xmltx.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {

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
