package com.SpringforNew.validationMethod;

import jakarta.validation.constraints.*;

public class User {
    @NotNull
    private String name;
    @Max(150)
    @Min(0)
    private Integer age;
    @Pattern(regexp = "^1(3|5|7|8|9)\\d{9}$", message = "手机格式错误")
    @NotBlank(message = "手机号不能为空")
    private String phone;
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Integer getAge() {return age;}
    public void setAge(Integer age) {this.age = age;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                '}';
    }
}
