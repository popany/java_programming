// https://www.logicbig.com/tutorials/spring-framework/spring-boot/maven-resource-filtering.html
// https://www.logicbig.com/tutorials/spring-framework/spring-boot/properties-place-holders.html

package com.example.property_expansion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class PropertyExpansionApplication implements CommandLineRunner {
    private static class FooBean {

        @Value("${app.info}")
        private String appInfo;

        public void print() {
            System.out.printf("App info: %s\n", appInfo);
        }
    }

    @Bean
    FooBean getFooBean() {
        return new FooBean();
    }

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(PropertyExpansionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        applicationContext.getBean(FooBean.class).print();
    }
}
