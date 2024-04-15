package com.SpringforNew.junit5te;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

//方式一
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration("classpath:bean.xml")
//方式二
@SpringJUnitConfig(locations = "classpath:bean.xml")
public class SpringJunit5Te {
    @Autowired
    private User user;
    @Test
    public void te(){
        System.out.println(user);
        user.work();
    }
}
