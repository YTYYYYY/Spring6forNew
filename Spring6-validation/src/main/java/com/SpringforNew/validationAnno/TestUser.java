package com.SpringforNew.validationAnno;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestUser {
    @Test
    public void te(){
        ApplicationContext context =
                new AnnotationConfigApplicationContext(ValidationConfig.class);
        UserService userValidator = context.getBean(UserService.class);
        User user = new User();
        user.setName("YTY");
        user.setAge(200);
        System.out.println(userValidator.validatorByUser(user));
    }
}
