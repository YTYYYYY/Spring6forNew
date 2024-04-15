package com.SpringforNew.junit4te;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:bean.xml")
public class SpringJunit4Te {
    @Autowired
    private Worker worker;
    @Test
    public void te(){
        System.out.println(worker);
        worker.work();
    }
}
