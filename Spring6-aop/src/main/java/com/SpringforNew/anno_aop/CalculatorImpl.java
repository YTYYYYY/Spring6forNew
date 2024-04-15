package com.SpringforNew.anno_aop;

import org.springframework.stereotype.Component;

@Component
public class CalculatorImpl implements Calculator {

    @Override
    public int add(int x, int y) {
        System.out.println("add...");
        int res = x+y;
        return res;
    }

    @Override
    public int sub(int x, int y) {
        System.out.println("sub...");
        int res = x-y;
        return res;
    }

    @Override
    public int mul(int x, int y) {
        System.out.println("mul...");
        int res = x*y;
        return res;
    }

    @Override
    public int div(int x, int y) {
        System.out.println("div...");
        int res = x/y;
        return res;
    }


}
