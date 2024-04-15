package com.SpringforNew.validationAnno;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class User {
    @NotNull
    private String name;
    @Max(150)
    @Min(0)
    private Integer age;
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Integer getAge() {return age;}
    public void setAge(Integer age) {this.age = age;}
}
