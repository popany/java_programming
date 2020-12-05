// https://www.tutorialspoint.com/spring/event_handling_in_spring.htm

package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppEventHandling 
{
    public static void main( String[] args )
    {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml")) {
            context.start();
            context.stop();
        }
    }
}
