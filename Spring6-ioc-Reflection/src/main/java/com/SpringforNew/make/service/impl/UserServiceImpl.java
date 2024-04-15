package com.SpringforNew.make.service.impl;

import com.SpringforNew.make.anno.Bean;
import com.SpringforNew.make.anno.Di;
import com.SpringforNew.make.dao.UserDao;
import com.SpringforNew.make.service.UserService;

@Bean
public class UserServiceImpl implements UserService {
    @Di
    private UserDao userDao;
    @Override
    public void add() {
        System.out.println("service...");
        userDao.add();
    }
}
