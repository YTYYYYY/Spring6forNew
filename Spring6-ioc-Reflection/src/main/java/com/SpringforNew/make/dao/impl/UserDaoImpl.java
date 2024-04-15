package com.SpringforNew.make.dao.impl;

import com.SpringforNew.make.anno.Bean;
import com.SpringforNew.make.dao.UserDao;

@Bean
public class UserDaoImpl implements UserDao {
    @Override
    public void add() { System.out.println("dao..."); }
}
