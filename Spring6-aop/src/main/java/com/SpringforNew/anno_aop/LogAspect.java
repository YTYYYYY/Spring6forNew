package com.SpringforNew.anno_aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect     //切面类
@Component  //ioc容器管理
public class LogAspect {
    /*
    *   设置切入点和通知类型
    *   切入点表达式：
    *       例：execution(public int com.SpringforNew.anno_aop.LogAspect.div(int,int))
    *       execution:固定格式
    *       public:权限修饰符
    *       int:方法返回值
    *           public int:用*表示权限修饰符和返回值任意
    *       com.SpringforNew.anno_aop:用*表示包名任意，用*..表示包名任意且包的层次深度任意
    *       .LogAspect:类名全部用*表示类名任意，类名部分用*表示，例如*Se表示匹配以Se结尾的类或接口
    *           com.SpringforNew.anno_aop.LogAspect:方法所在类型的全类名
    *       .div:方法名。方法名全部用*表示方法名任意，方法名部分用*表示，例如get*表示匹配以get开头的方法
    *       (int,int):参数列表，用(..)表示参数列表任意
    *   通知类型：
    *       前置：@Before(value="切入点表达式配置切入点")
    *       返回：@AfterReturning
    *       异常：@AfterThrowing
    *       后置：@After()
    *       环绕：@Around()
    * */

    @Pointcut("execution(* com.SpringforNew.anno_aop.*.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void beforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[Logger-before]name:"+methodName+",args:"+Arrays.toString(joinPoint.getArgs()));
    }

    @After("pointCut()")
    public void afterMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[Logger-after]name"+methodName+",args:"+Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "pointCut()",returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint,Object result){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[Logger-afterReturning]name:"+methodName+",res:"+result);
    }

    //目标方法有异常这个会执行
    @AfterThrowing(value = "pointCut()",throwing = "ex")
    public void afterThrowingMethod(JoinPoint joinPoint,Throwable ex){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[Logger-afterThrowing]name:"+methodName+",error:"+ex);
    }

    @Around("pointCut()")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint){
        String methodName = proceedingJoinPoint.getSignature().getName();
        String args = Arrays.toString(proceedingJoinPoint.getArgs());
        Object result = null;
        try {
            System.out.println("[Logger-around-before]name:"+methodName+",args:"+args);
            result = proceedingJoinPoint.proceed();
            System.out.println("[Logger-around-afterReturning]name:"+methodName+",res:"+result);
        }catch (Throwable throwable){
            System.out.println("[Logger-around-afterThrowing]name:"+methodName+",error:"+throwable);
        }finally {
            System.out.println("[Logger-around-after]name"+methodName+",args:"+args);
        }
        return result;
    }
}
