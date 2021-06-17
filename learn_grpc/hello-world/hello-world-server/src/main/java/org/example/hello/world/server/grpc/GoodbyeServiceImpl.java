package org.example.hello.world.server.grpc;

import org.example.hello.world.grpc.GoodbyeRequest;
import org.example.hello.world.grpc.GoodbyeResponse;
import org.example.hello.world.grpc.GoodbyeServiceGrpc.GoodbyeServiceImplBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.stub.StreamObserver;

public class GoodbyeServiceImpl extends GoodbyeServiceImplBase{

    private static final Logger logger = LoggerFactory.getLogger(GoodbyeServiceImpl.class);

    @Override
    public void sayGoodbye(GoodbyeRequest request, StreamObserver<GoodbyeResponse> responseObserver) {

        logger.info("Request received from client: {}", request);

        String greeting = new StringBuilder()
            .append("Goodbye, ")
            .append(request.getFirstName())
            .append(" ")
            .append(request.getLastName())
            .toString();

        GoodbyeResponse goodbyeResponse = GoodbyeResponse.newBuilder()
            .setGreeting(greeting)
            .build();

        responseObserver.onNext(goodbyeResponse);
        responseObserver.onCompleted();  
    }

    @Override
    public void sayGoodbyeAgain(GoodbyeRequest request, StreamObserver<GoodbyeResponse> responseObserver) {

        logger.info("Request received from client: {}", request);

        String greeting = new StringBuilder()
            .append("Goodbye Again, ")
            .append(request.getFirstName())
            .append(" ")
            .append(request.getLastName())
            .toString();

        GoodbyeResponse goodbyeResponse = GoodbyeResponse.newBuilder()
            .setGreeting(greeting)
            .build();

        responseObserver.onNext(goodbyeResponse);
        responseObserver.onCompleted();  
    }



}
