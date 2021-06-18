package org.example.hello.world.client;

import io.grpc.ManagedChannel;

import org.example.hello.world.grpc.GoodbyeServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.hello.world.grpc.GoodbyeRequest;
import org.example.hello.world.grpc.GoodbyeResponse;

public class GoodbyeClient {

    private static final Logger logger = LoggerFactory.getLogger(GoodbyeClient.class);

    private GoodbyeServiceGrpc.GoodbyeServiceBlockingStub goodbyeServiceBlockingStub;

    public GoodbyeClient(ManagedChannel channel) {
        goodbyeServiceBlockingStub = GoodbyeServiceGrpc.newBlockingStub(channel);
    }

    public void sayGoodbye() {
        GoodbyeResponse goodbyeResponse = goodbyeServiceBlockingStub.sayGoodbye(GoodbyeRequest.newBuilder()
            .setFirstName("Foo")
            .setLastName("Bar")
            .build());

        logger.info("Response received from server: {}", goodbyeResponse);
    }

    public void sayGoodbyeAgain() {
        GoodbyeResponse goodbyeResponse = goodbyeServiceBlockingStub.sayGoodbyeAgain(GoodbyeRequest.newBuilder()
            .setFirstName("Foo")
            .setLastName("Bar")
            .build());

        logger.info("Response received from server: {}", goodbyeResponse);
    }

}
