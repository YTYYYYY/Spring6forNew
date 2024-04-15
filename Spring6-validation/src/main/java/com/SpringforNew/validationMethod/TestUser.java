package com.SpringforNew.validationMethod;

import com.SpringforNew.validationMethod.User;
import com.SpringforNew.validationMethod.UserService;
import com.SpringforNew.validationMethod.ValidationConfigNew;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestUser {
    @Test
    public void te(){
        ApplicationContext context =
                new AnnotationConfigApplicationContext(ValidationConfigNew.class);
        UserService userService = context.getBean(UserService.class);
        User user = new User();
        user.setName("YTY");
        user.setAge(100);
        user.setPhone("13344445555");
        System.out.println(userService.testMethod(user));
    }
}
