// https://www.tutorialspoint.com/spring/spring_beans_autowiring.htm
// https://www.tutorialspoint.com/spring/spring_autowiring_byname.htm
// https://www.tutorialspoint.com/spring/spring_autowiring_bytype.htm
// https://www.tutorialspoint.com/spring/spring_autowiring_byconstructor.htm
// https://www.qikegu.com/docs/1568

package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppAutowiring 
{
    public static void main( String[] args )
    {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml")) {
            Foo foo = (Foo) context.getBean("foo");
            System.out.println("foo - " + foo.toString());
        }
    }
}
