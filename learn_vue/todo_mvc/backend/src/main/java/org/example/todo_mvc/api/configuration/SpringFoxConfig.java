package org.example.todo_mvc.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    // urls:
    // http://localhost:50000/swagger-ui/index.html
    // http://localhost:50000/swagger-resources
    // http://localhost:50000/v3/api-docs
    // http://localhost:50000/v2/api-docs

    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
        .select()                                  
        .apis(RequestHandlerSelectors.any())              
        .paths(PathSelectors.any())                          
        .build()
        .apiInfo(apiInfo());                                           
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Todo API")
                .description("Todo Api")
                .build();
    }
}
