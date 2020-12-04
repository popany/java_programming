package org.example;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;

@Aspect
public class Foo {

    @Pointcut("execution(* org.example.Bar.*(..))")
    private void selectAll() {}

    @Pointcut("execution(* org.example.Bar.needBefore(..))")
    private void selectBefore() {}

    @Pointcut("execution(* org.example.Bar.needAfter(..))")
    private void selectAfter() {}

    @Pointcut("execution(* org.example.Bar.needAround(..))")
    private void selectAround() {}

    @Pointcut("execution(* org.example.Bar.needBeforeAfterAround(..))")
    private void selectBeforeAfterAround() {}

    @Before("selectBefore() || selectBeforeAfterAround()")
    void func1Before() {
        System.out.println("before: func1()");
    }

    @After("selectAfter() || selectBeforeAfterAround()")
    void func2After() {
        System.out.println("after: func2()");
    }

    @Around("selectAround() || selectBeforeAfterAround()")
    void func3Around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around: func3() enter");

        pjp.proceed(pjp.getArgs());

        System.out.println("around: func3() exit");
    }

    @AfterThrowing(pointcut = "selectAll()", throwing = "ex")
    void func4AfterThrowing(Exception ex) {
        System.out.println("after-throwing: func4(), exception: " + ex.getMessage());
    }

    @AfterReturning(pointcut = "selectAll()", returning = "obj")
    void func5AfterReturning(Object obj) {
        if (obj == null) {
            System.out.println("after-returning: func5(), obj == null");
            return;
        }
        System.out.println("after-returning: func5(), return: " + obj.toString());
    }
}
