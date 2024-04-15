package com.SpringforNew.jdbc_xmltx.dao;

public interface UserDao {
    Integer getMoneyById(Integer userId);

    void updateMoney(Integer userId, Integer updatedMoney);
}
