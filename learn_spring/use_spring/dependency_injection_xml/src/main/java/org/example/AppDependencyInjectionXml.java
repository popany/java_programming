// https://www.tutorialspoint.com/spring/spring_dependency_injection.htm
// https://www.qikegu.com/docs/1567
package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppDependencyInjectionXml 
{
    public static void main( String[] args )
    {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml")) {
            Foo foo1 = (Foo) context.getBean("foo1");
            Foo foo2 = (Foo) context.getBean("foo2");
            Foo foo3 = (Foo) context.getBean("foo3");
            System.out.println("foo1 - " + foo1.toString());
            System.out.println("foo2 - " + foo2.toString());
            System.out.println("foo3 - " + foo3.toString());
        }
    }
}
