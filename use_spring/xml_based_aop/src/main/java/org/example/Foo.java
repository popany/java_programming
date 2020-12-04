package org.example;

import org.aspectj.lang.ProceedingJoinPoint;

public class Foo {
    void func1Before() {
        System.out.println("before: func1()");
    }

    void func2After() {
        System.out.println("after: func2()");
    }

    void func3Around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around: func3() enter");

        pjp.proceed(pjp.getArgs());

        System.out.println("around: func3() exit");
    }

    void func4AfterThrowing(Exception ex) {
        System.out.println("after-throwing: func4(), exception: " + ex.getMessage());
    }

    void func5AfterReturning(Object obj) {
        if (obj == null) {
            System.out.println("after-returning: func5(), obj == null");
            return;
        }
        System.out.println("after-returning: func5(), return: " + obj.toString());
    }
}
