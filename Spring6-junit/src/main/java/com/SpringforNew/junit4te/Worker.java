package com.SpringforNew.junit4te;

import org.springframework.stereotype.Component;

@Component
public class Worker {
    private  String name;
    public void work(){
        System.out.println("work..");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
