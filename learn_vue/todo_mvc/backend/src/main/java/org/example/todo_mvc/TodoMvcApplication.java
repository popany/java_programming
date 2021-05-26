package org.example.todo_mvc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.todo_mvc.*.mapper")
public class TodoMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoMvcApplication.class, args);
    }
}
