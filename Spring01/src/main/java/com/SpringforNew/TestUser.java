package com.SpringforNew;




import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestUser {

    private Logger logger = LoggerFactory.getLogger(TestUser.class);

    //反射创建对象
    @Test
    public void testUserObject() throws Exception {
        //获取类class对象
        Class<?> cla = Class.forName("com.SpringforNew.User");
        //调用方法创建对象
        //Object o = cla.newInstance();
        User user = (User)cla.getDeclaredConstructor().newInstance();
        System.out.println(user);
    }

    @Test
    public void te1(){
        //加载Spring配置文件，对象创建
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean.xml");
        //获取创建的对象
        User user = (User)context.getBean("user");
        System.out.println(user);
        logger.info("ok!!!");
        //使用对象调用方法进行测试
        user.add();
    }
}
