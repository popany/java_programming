package org.example.todo_mvc.api.configuration;

import org.example.todo_mvc.api.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {

    public static final String LOGIN_INTERCEPTOR_PATH_PATTERN = "/**/*";

    public static final String LOGIN_PATH_PATTERN = "/login";

    @Bean
    public LoginHandlerInterceptor loginInterceptor() {
        return new LoginHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
            .addPathPatterns(LOGIN_INTERCEPTOR_PATH_PATTERN)
            .excludePathPatterns(LOGIN_PATH_PATTERN,
                "/swagger-resources/**",
                "/webjars/**",
                "/v2/**",
                "/v3/**",
                "*.html",
                "/swagger-ui/**",
                "/doc.html");
    }

}
