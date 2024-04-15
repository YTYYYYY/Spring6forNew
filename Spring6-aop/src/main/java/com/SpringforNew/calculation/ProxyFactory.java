package com.SpringforNew.calculation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ProxyFactory {

    //目标对象
    private Object target;
    public ProxyFactory(Object target){
        this.target = target;
    }
    //返回代理对象
    public Object getProxy(){
        /*
        *   Proxy.newProxyInstance(ClassLoader loader,
        *                          Class<?>[] interfaces,
        *                          InvocationHandler h)
        *   ClassLoader：加载动态生成代理类的类加载器
        *   Class<?>[] interfaces：目标对象实现的所有接口的class类型数组
        *   InvocationHandler：设置代理对象实现目标对象的过程
        * */
        ClassLoader loader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        /*
        *   Object proxy：代理对象
        *   Method method：需要重写目标对象的方法
        *   Object[] args：method方法的参数
        * */
        InvocationHandler h = (proxy, method, args) -> {
            //调用方法之前输出
            System.out.println("[Logs]:"+method.getName()+",args:"+ Arrays.toString(args));
            //调用目标方法
            Object result = method.invoke(target, args);
            //调用方法之后输出
            System.out.println("[Logs]:"+method.getName()+",result:"+ result);
            return result;
        };
        return Proxy.newProxyInstance(loader,interfaces,h);
    }
}
