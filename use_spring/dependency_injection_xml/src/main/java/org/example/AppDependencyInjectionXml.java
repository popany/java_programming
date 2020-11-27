package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class AppDependencyInjectionXml 
{
    public static void main( String[] args )
    {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml")) {
            Foo foo1 = (Foo) context.getBean("foo1");
            Foo foo2 = (Foo) context.getBean("foo2");
            System.out.println(foo1.toString());
            System.out.println(foo2.toString());
        }
    }
}
