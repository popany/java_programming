package com.example.use_hive_jdbc;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class UseHiveJdbcApplication implements CommandLineRunner {

    @Autowired
    @Qualifier("hiveJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(UseHiveJdbcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String sql = "show tables";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        Iterator<Map<String, Object>> it = rows.iterator();
        while (it.hasNext()) {
            Map<String, Object> row = it.next();
            System.out.println(String.format("%s", row.toString()));
        }    
    }
}
