package com.SpringforNew.jdbc_tx.dao;

public interface BookDao {
    Integer getPriceById(Integer bookId);

    Integer getNumById(Integer bookId);

    void updateNum(Integer bookId, Integer updatedNum);
}
