package com.SpringforNew.calculation;

public class CalculatorLogsImpl implements Calculator{

    @Override
    public int add(int x, int y) {
        System.out.println("[Log]: add() start.");
        System.out.println("add...");
        int res = x+y;
        System.out.println("[Log]: add() end.");
        return res;
    }

    @Override
    public int sub(int x, int y) {
        System.out.println("[Log]: sub() start.");
        System.out.println("sub...");
        int res = x-y;
        System.out.println("[Log]: sub() end.");
        return res;
    }

    @Override
    public int mul(int x, int y) {
        System.out.println("[Log]: mul() start.");
        System.out.println("mul...");
        int res = x*y;
        System.out.println("[Log]: mul() end.");
        return res;
    }

    @Override
    public int div(int x, int y) {
        System.out.println("[Log]: div() start.");
        System.out.println("div...");
        int res = x/y;
        System.out.println("[Log]: div() end.");
        return res;
    }
}
