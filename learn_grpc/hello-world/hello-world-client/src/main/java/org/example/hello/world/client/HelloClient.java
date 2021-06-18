package org.example.hello.world.client;

import io.grpc.ManagedChannel;

import org.example.hello.world.grpc.HelloServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.example.hello.world.grpc.HelloRequest;
import org.example.hello.world.grpc.HelloResponse;

public class HelloClient {

    private static final Logger logger = LoggerFactory.getLogger(HelloClient.class);

    private HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub;

    public HelloClient(ManagedChannel channel) {
        helloServiceBlockingStub = HelloServiceGrpc.newBlockingStub(channel);
    }

    public void sayHello() {
        HelloResponse helloResponse = helloServiceBlockingStub.sayHello(HelloRequest.newBuilder()
            .setFirstName("Foo")
            .setLastName("Bar")
            .build());

        logger.info("Response received from server: {}", helloResponse);
    }

    public void sayHelloAgain() {
        HelloResponse helloResponse = helloServiceBlockingStub.sayHelloAgain(HelloRequest.newBuilder()
            .setFirstName("Foo")
            .setLastName("Bar")
            .build());

        logger.info("Response received from server: {}", helloResponse);
    }

}
