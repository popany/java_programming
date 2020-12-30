package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppCustomEvents 
{
    public static void main( String[] args )
    {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml")) {
            CustomEventPublisher cvp = (CustomEventPublisher) context.getBean("customEventPublisher");
      
            cvp.publish();  
            cvp.publish();
        }
    }
}
