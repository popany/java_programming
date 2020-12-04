package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppAnnotationBasedAop 
{
    public static void main( String[] args )
    {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml")) {
            Bar bar = (Bar) context.getBean("bar");
            System.out.println("============================================");
            bar.needBefore();
            System.out.println("--------------------------------------------");

            System.out.println("============================================");
            bar.needAfter();
            System.out.println("--------------------------------------------");

            System.out.println("============================================");
            bar.needAround("a", "b");
            System.out.println("--------------------------------------------");

            System.out.println("============================================");
            bar.needBeforeAfterAround();
            System.out.println("--------------------------------------------");

            System.out.println("============================================");
            try {
                bar.needAfterThrowing(1);
            } catch (Exception ex) {
                System.out.println("exception: " + ex.getMessage());
            }
            System.out.println("--------------------------------------------");

            System.out.println("============================================");
            bar.needAfterReturning();
            System.out.println("--------------------------------------------");
        }
    }
}
