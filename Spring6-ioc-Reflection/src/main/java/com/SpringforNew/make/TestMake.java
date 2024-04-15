package com.SpringforNew.make;

import com.SpringforNew.make.bean.AnnoApContext;
import com.SpringforNew.make.bean.ApContext;
import com.SpringforNew.make.service.UserService;

public class TestMake {
    public static void main(String[] args) {
        ApContext context = new AnnoApContext("com.SpringforNew.make");
        UserService userService = (UserService)context.getBean(UserService.class);
        userService.add();
    }
}
