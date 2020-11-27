package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppBeanLifeCycle 
{
    public static void main( String[] args )
    {
        System.out.println("======================================");
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml")) {
            Foo obj = (Foo) context.getBean("foo");
            System.out.println(obj.toString());
        }
        System.out.println("--------------------------------------");
    }
}
