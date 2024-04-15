package com.SpringforNew.bitest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class Emp {

    private Dept dept;
    private String ename;
    private String[] hobbies;

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    private Integer age;

    public void work(){
        System.out.println(ename+"working,"+age);
        dept.info();
    }

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean-biarray.xml");
        Emp emp = context.getBean("emp", Emp.class);
        System.out.println(Arrays.toString(emp.hobbies));
    }
}
