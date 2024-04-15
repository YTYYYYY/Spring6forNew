package com.SpringforNew.anno_resource.dao;

import org.springframework.stereotype.Repository;

@Repository("myUserDao")
public class UserDaoImpl implements UserDao{
    @Override
    public void addUser() {
        System.out.println("UserDao...");
    }
}
