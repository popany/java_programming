package org.example.query_employee_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
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
        SpringApplication application = new SpringApplication(QueryEmployeeApiApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Override
    public void run(String... args) throws URISyntaxException {
        addEmployee();
        listEmployee();
    }

    private void listEmployee() throws URISyntaxException {
        System.out.println("<listEmployee>");

        RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:8080/employees/";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        System.out.println("Status: " + result.getStatusCode());
        System.out.println("Headers: " + result.getHeaders());
        System.out.println("Body: " + result.getBody());
    }

    private void addEmployee() throws URISyntaxException {
        System.out.println("<addEmployee>");

        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/employees/";
        URI uri = new URI(baseUrl);
        Employee employee = new Employee(null, "Adam", "Gilly", "test@email.com");
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");      

        HttpEntity<Employee> request = new HttpEntity<>(employee, headers);
        
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

        System.out.println("Status: " + result.getStatusCode());
        System.out.println("Headers: " + result.getHeaders());
        System.out.println("Body: " + result.getBody());
    }

}
