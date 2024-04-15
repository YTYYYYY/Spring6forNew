package com.SpringforNew;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestCar {

    @Test
    public void te1() throws Exception {
        Class cla = Class.forName("com.SpringforNew.Car");
        Car car = (Car)cla.getDeclaredConstructor().newInstance();
        Constructor[] constructors = cla.getDeclaredConstructors();
        for(Constructor c : constructors){
            System.out.println(c.getName()+","+c.getParameterCount());
        }
        //com.SpringforNew.Car,0
        //com.SpringforNew.Car,3
    }

    @Test       //有参构造 public
    public void te2() throws Exception {
        Class cla = Class.forName("com.SpringforNew.Car");
        Constructor claDecConstructor
                = cla.getDeclaredConstructor(String.class, String.class, int.class);
        Car car = (Car)claDecConstructor.newInstance("宝马", "白色", 120000);
    }

    @Test       //有参构造 private
    public void te3() throws Exception {
        Class cla = Class.forName("com.SpringforNew.Car");
        Constructor claDecConstructor
                = cla.getDeclaredConstructor(String.class, String.class, int.class);
        claDecConstructor.setAccessible(true);
        Car car = (Car)claDecConstructor.newInstance("宝马", "白色", 120000);
        System.out.println(car.toString());
    }

    @Test       //获取属性
    public void te4() throws Exception {
        Class cla = Class.forName("com.SpringforNew.Car");
        Car car = (Car)cla.getDeclaredConstructor().newInstance();
        Field[] declaredFields = cla.getDeclaredFields();
        for(Field f:declaredFields){
            if("name".equals(f.getName())){
                f.setAccessible(true);
                f.set(car,"奥迪");
            }
        }
        System.out.println(car);    //Car{name='奥迪', color='null', price=0}
    }

    @Test       //获取方法
    public void te5() throws Exception {
        Class cla = Class.forName("com.SpringforNew.Car");
        Car car = (Car)cla.getDeclaredConstructor().newInstance();
        Method[] declaredMethods = cla.getDeclaredMethods();
        for(Method m : declaredMethods){
            if("setName".equals(m.getName())){
                m.invoke(car,"奥迪");
            }
        }
        System.out.println(car);    //Car{name='奥迪', color='null', price=0}
    }
}
