package com.SpringforNew.jdbc_tx.service;

import com.SpringforNew.jdbc_tx.dao.BookDao;
import com.SpringforNew.jdbc_tx.dao.UserDao;
import com.sun.jdi.ShortType;
import org.apache.logging.log4j.core.util.CloseShieldOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(timeout = 3)
@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookDao bookDao;
    @Autowired
    private UserDao userDao;

    @Transactional(noRollbackFor = ArithmeticException.class)
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
