package com.SpringforNew.jdbc_xmltx.service;

import com.SpringforNew.jdbc_xmltx.dao.BookDao;
import com.SpringforNew.jdbc_xmltx.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private UserDao userDao;

    @Override
    public void buyBook(Integer bookId,Integer userId) {
        //获取图书库存
        Integer bookNum = bookDao.getNumById(bookId);
        //获取图书价格
        Integer bookPrice = bookDao.getPriceById(bookId);
        //获取用户余额
        Integer userMoney = userDao.getMoneyById(userId);
        //更新图书库存
        bookDao.updateNum(bookId,(bookNum-1));
        //更新用户余额
        userDao.updateMoney(userId,(userMoney-bookPrice));
    }
}
