// https://www.tutorialspoint.com/spring/spring_annotation_based_configuration.htm
// https://www.tutorialspoint.com/spring/spring_autowired_annotation.htm
// https://www.tutorialspoint.com/spring/spring_qualifier_annotation.htm
// https://www.qikegu.com/docs/1569

package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppAnnocationBasedConfiguration 
{
    public static void main( String[] args )
    {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml")) {
            Foo foo = (Foo) context.getBean("foo");
            System.out.println("foo: " + foo.toString());
        }
    }
}
