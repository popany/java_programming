// https://www.tutorialspoint.com/spring/spring_hello_world_example.htm

package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppHelloWorld 
{
    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml")) {
            HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
            obj.getMessage();
        }
    }
}
