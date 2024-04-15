package com.SpringforNew.jdbc_tx.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer getMoneyById(Integer userId) {
        return jdbcTemplate.queryForObject(
                "SELECT money FROM spring_jdbc_bookshop.user WHERE id=?",
                Integer.class,userId);
    }

    @Override
    public void updateMoney(Integer userId, Integer updatedMoney) {
        jdbcTemplate.update(
                "UPDATE spring_jdbc_bookshop.user SET money=? WHERE id=?",
                updatedMoney,userId);
    }
}
