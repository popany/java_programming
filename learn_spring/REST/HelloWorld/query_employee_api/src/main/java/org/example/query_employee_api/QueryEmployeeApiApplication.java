package org.example.query_employee_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class QueryEmployeeApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(QueryEmployeeApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws URISyntaxException {
        listEmployee();
    }

    private void addEmployee() {

    }

    private void listEmployee() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/employees/";
        URI uri = new URI(baseUrl);
        Employee employee = new Employee(null, "Adam", "Gilly", "test@email.com");
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");      

        HttpEntity<Employee> request = new HttpEntity<>(employee, headers);
        
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        System.out.println(result);
    }

}
