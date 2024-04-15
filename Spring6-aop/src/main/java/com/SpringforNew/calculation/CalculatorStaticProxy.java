package com.SpringforNew.calculation;

public class CalculatorStaticProxy implements Calculator{

    private Calculator calculator;
    public CalculatorStaticProxy(Calculator calculator) {
        this.calculator = calculator;
    }
    @Override
    public int add(int x, int y) {
        System.out.println("[Log]: add() start.");
        System.out.println("add...");
        int res = calculator.add(x,y);
        System.out.println("[Log]: add() end.");
        return res;
    }

    @Override
    public int sub(int x, int y) {
        return 0;
    }

    @Override
    public int mul(int x, int y) {
        return 0;
    }

    @Override
    public int div(int x, int y) {
        return 0;
    }
}
