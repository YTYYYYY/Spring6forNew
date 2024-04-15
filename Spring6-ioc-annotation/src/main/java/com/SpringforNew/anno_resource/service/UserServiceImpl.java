package com.SpringforNew.anno_resource.service;

import com.SpringforNew.anno_resource.dao.UserDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("myUserService")
public class UserServiceImpl implements UserService{

    @Resource
    private UserDao myUserDao;

    @Override
    public void addUser() {
        System.out.println("UserService...");
        myUserDao.addUser();
    }
}
