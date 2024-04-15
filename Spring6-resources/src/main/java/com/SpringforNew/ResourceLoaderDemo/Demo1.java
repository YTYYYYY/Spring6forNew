package com.SpringforNew.ResourceLoaderDemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

public class Demo1 {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext();
        //通过ApplicationContext访问资源
        //ApplicationContext实例获取Resource实例时
        //默认使用与ApplicationContext相同的资源访问策略
        Resource resource = context.getResource("Demo.txt");
        System.out.println(resource.getFilename());
    }
}
