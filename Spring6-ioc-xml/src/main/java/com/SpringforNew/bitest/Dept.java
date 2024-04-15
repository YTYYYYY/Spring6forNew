package com.SpringforNew.bitest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;

public class Dept {

    private String dname;
    private List<Emp> empList;

    public void info(){
        System.out.println("部门："+dname);
        for(Emp emp : empList){
            System.out.println(emp.getEname());
        }
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public List<Emp> getEmpList() {
        return empList;
    }

    public void setEmpList(List<Emp> empList) {
        this.empList = empList;
    }

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean-biarray.xml");
        Dept dept = context.getBean("dept", Dept.class);
        dept.info();
    }
}
