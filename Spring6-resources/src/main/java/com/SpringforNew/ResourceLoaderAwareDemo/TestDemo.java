package com.SpringforNew.ResourceLoaderAwareDemo;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ResourceLoader;

public class TestDemo {
    @Test
    public void te(){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean-test.xml");
        TestBean testBean = context.getBean(TestBean.class);
        ResourceLoader resourceLoader = testBean.getResourceLoader();
        System.out.println(resourceLoader == context);
    }
}
