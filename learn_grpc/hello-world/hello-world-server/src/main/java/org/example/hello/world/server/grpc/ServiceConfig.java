package org.example.hello.world.server.grpc;

import org.example.hello.world.server.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Autowired
    ServerConfig serverConfig;

    @Bean
    ChatServiceImpl getChatServiceImpl() {
        return new ChatServiceImpl(serverConfig.getGrpcServerChatservicethreadpoolsize());
    }
    
    @Bean
    HelloServiceImpl getHelloServiceImpl() {
        return new HelloServiceImpl();
    }

    @Bean
    GoodbyeServiceImpl getGoodbyeServiceImpl() {
        return new GoodbyeServiceImpl();
    }
}
