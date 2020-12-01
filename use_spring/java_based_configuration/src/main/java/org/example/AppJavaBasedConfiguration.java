// https://www.tutorialspoint.com/spring/spring_java_based_configuration.htm
// https://www.qikegu.com/docs/1817

package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppJavaBasedConfiguration 
{
    public static void main( String[] args ) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FooConfig.class)) {
            Foo foo = (Foo) context.getBean("foo");
            System.out.println("foo - " + foo.toString());
        }
    }
}
