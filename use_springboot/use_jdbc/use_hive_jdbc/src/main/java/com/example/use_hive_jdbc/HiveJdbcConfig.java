package com.example.use_hive_jdbc;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class HiveJdbcConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl(env.getProperty("hive.url"));
        dataSource.setDriverClassName(env.getProperty("hive.driver-class-name"));
        dataSource.setUsername(env.getProperty("hive.user"));
        dataSource.setPassword(env.getProperty("hive.password"));
        return dataSource;
    }

    @Bean(name = "hiveJdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
