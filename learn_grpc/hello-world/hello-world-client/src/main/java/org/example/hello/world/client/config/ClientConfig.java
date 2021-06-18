package org.example.hello.world.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
@PropertySource(value = "client.properties")
public class ClientConfig {

    @Value("${grpc.server.host:localhost}")
    private String grpcServerHost;

    @Value("${grpc.server.prot:50008}")
    private int grpcServerPort;

    @Value("${grpc.client.threadpoolsize:10}")
    private int grpcClientThreadpoolsize;
}
