package com.SpringforNew.jdbc_tx.dao;

public interface UserDao {
    Integer getMoneyById(Integer userId);

    void updateMoney(Integer userId, Integer updatedMoney);
}
