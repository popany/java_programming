// https://www.tutorialspoint.com/spring/aop_with_spring.htm
// https://www.tutorialspoint.com/spring/schema_based_aop_appoach.htm
// https://www.qikegu.com/docs/1820

package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppXmlBasedAop 
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
            bar.needAround();
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
