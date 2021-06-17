package org.example.hello.world.server.grpc;

import org.example.hello.world.grpc.ByeRequest;
import org.example.hello.world.grpc.ByeResponse;
import org.example.hello.world.grpc.ByeServiceGrpc.ByeServiceImplBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.stub.StreamObserver;

public class ByeServiceImpl extends ByeServiceImplBase{

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public void bye(ByeRequest request, StreamObserver<ByeResponse> responseObserver) {

        logger.info("Request received from client: {}", request);

        String greeting = new StringBuilder()
            .append("Bye, ")
            .append(request.getFirstName())
            .append(" ")
            .append(request.getLastName())
            .toString();

        ByeResponse byeResponse = ByeResponse.newBuilder()
            .setGreeting(greeting)
            .build();

        responseObserver.onNext(byeResponse);
        responseObserver.onCompleted();  
    }

}
