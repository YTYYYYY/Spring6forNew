package com.SpringforNew.validatorImpl;

import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

public class TestPerson {
    public static void main(String[] args) {
        //创建person对象
        Person person = new Person();
        person.setName("");
        person.setAge(200);
        //创建person对应databinder
        DataBinder dataBinder = new DataBinder(person);
        //设置校验器
        dataBinder.setValidator(new PersonValidator());
        //调用方法进行校验
        dataBinder.validate();
        //输出校验结果
        BindingResult result = dataBinder.getBindingResult();
        System.out.println(result.getAllErrors());
        System.out.println(result.getErrorCount());
    }
}
