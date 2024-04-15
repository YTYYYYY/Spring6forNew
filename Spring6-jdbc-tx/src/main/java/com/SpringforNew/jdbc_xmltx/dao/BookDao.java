package com.SpringforNew.jdbc_xmltx.dao;

public interface BookDao {
    Integer getPriceById(Integer bookId);

    Integer getNumById(Integer bookId);

    void updateNum(Integer bookId, Integer updatedNum);
}
