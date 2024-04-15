package com.SpringforNew.autoxml.service;

import com.SpringforNew.autoxml.dao.UserDao;
import com.SpringforNew.autoxml.dao.UserDaoImpl;

public class UserServiceImpl implements UserService{
    //UserDao userDao = new UserDaoImpl();
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUserService() {
        System.out.println("UserService...");
        userDao.addUserDao();
    }
}
