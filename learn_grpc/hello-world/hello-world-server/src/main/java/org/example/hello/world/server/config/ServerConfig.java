package org.example.hello.world.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
@PropertySource(value = "server.properties")
public class ServerConfig {

    @Value("${grpc.server.port:50008}")
    private int grpcServerPort;

    @Value("${grpc.server.chatservicethreadpoolsize:10}")
    private int grpcServerChatservicethreadpoolsize;

    @Value("${grpc.server.threadpoolsize:10}")
    private int grpcServerThreadpoolsize;
}
