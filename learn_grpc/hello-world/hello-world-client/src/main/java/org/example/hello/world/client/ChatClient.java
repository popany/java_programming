package org.example.hello.world.client;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ThreadPoolExecutor;

import com.google.common.util.concurrent.ListenableFuture;

import org.example.hello.world.grpc.ChatServiceGrpc;
import org.example.hello.world.grpc.ClientWords;
import org.example.hello.world.grpc.ServerWords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatClient {

    private static final Logger logger = LoggerFactory.getLogger(ChatClient.class);

    private ChatServiceGrpc.ChatServiceBlockingStub chatServiceBlockingStub;
    private ChatServiceGrpc.ChatServiceFutureStub chatServiceFutureStub;
    private ChatServiceGrpc.ChatServiceStub chatServiceAsyncStub;

    public ChatClient(ManagedChannel channel, ThreadPoolExecutor threadPoolExecutor) {
        chatServiceBlockingStub = ChatServiceGrpc.newBlockingStub(channel);
        chatServiceFutureStub = ChatServiceGrpc.newFutureStub(channel);
        chatServiceAsyncStub = ChatServiceGrpc.newStub(channel).withExecutor(threadPoolExecutor);
    }

    public void blockingGreet() {
        ServerWords serverWords = chatServiceBlockingStub.greet(ClientWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
            .setContent("hello").build());

        logger.info("blockingGreet received from server: {}", serverWords);
    }

    public void blockingListen() {
        Iterator<ServerWords> serverWordsIterator = chatServiceBlockingStub.listen(ClientWords.newBuilder()
                .setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
                .setContent("speaking").build());

        logger.info("blockingListen received from server:");
        while (serverWordsIterator.hasNext()) {
            ServerWords serverWords = serverWordsIterator.next();
            logger.info("> {}", serverWords);
        }
        logger.info(">#");
    }

    public void futureGreet() {
        ListenableFuture<ServerWords> serverWordsFuture = chatServiceFutureStub.greet(ClientWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
            .setContent("hello").build());

        try {
            ServerWords serverWords = serverWordsFuture.get(); 
            logger.info("futureGreet received from server: {}", serverWords);
        }
        catch (Exception e) {
            logger.error("futureGreet error: {}", e.getMessage(), e);
        }
    }

    public void asyncGreet() {
        ClientWords clientWords = ClientWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
            .setContent("hello").build();

        chatServiceAsyncStub.greet(clientWords, new StreamObserver<ServerWords>() {

            @Override
            public void onNext(ServerWords serverWords) {
                logger.info("asyncGreet received from server: {}", serverWords);
            }

            @Override
            public void onError(Throwable t) {
                logger.error("asyncGreet error: {}", t.getMessage(), t);
            }

            @Override
            public void onCompleted() {
                logger.info("asyncGreet complete");
            }
        });
    }

    public void asyncListen() {
        ClientWords clientWords = ClientWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
            .setContent("speaking").build();

        chatServiceAsyncStub.listen(clientWords, new StreamObserver<ServerWords>() {

            @Override
            public void onNext(ServerWords serverWords) {
                logger.info("asyncListen received from server: {}", serverWords);
            }

            @Override
            public void onError(Throwable t) {
                logger.error("asyncListen error: {}", t.getMessage(), t);
            }

            @Override
            public void onCompleted() {
                logger.info("asyncListen complete");
            }
        });
    }

    public void asyncSpeak() {
        StreamObserver<ClientWords> clientWordsObserver = chatServiceAsyncStub.speak(new StreamObserver<ServerWords>() {

            @Override
            public void onNext(ServerWords serverWords) {
                logger.info("asyncSpeak received from server: {}", serverWords);
            }

            @Override
            public void onError(Throwable t) {
                logger.error("asyncSpeak error: {}", t.getMessage(), t);
            }

            @Override
            public void onCompleted() {
                logger.info("asyncSpeak complete");
            }
        });

        clientWordsObserver.onNext(ClientWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
            .setContent("1111").build());
        
        clientWordsObserver.onNext(ClientWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
            .setContent("2222").build());

        clientWordsObserver.onNext(ClientWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
            .setContent("3333").build());

        clientWordsObserver.onCompleted();

    }

    public void asyncTalk() {
        StreamObserver<ClientWords> clientWordsObserver = chatServiceAsyncStub.talk(new StreamObserver<ServerWords>() {

            @Override
            public void onNext(ServerWords serverWords) {
                logger.info("asyncTalk received from server: {}", serverWords);
            }

            @Override
            public void onError(Throwable t) {
                logger.error("asyncTalk error: {}", t.getMessage(), t);
            }

            @Override
            public void onCompleted() {
                logger.info("asyncTalk complete");
            }
        });

        clientWordsObserver.onNext(ClientWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
            .setContent("xxxx").build());
        
        clientWordsObserver.onNext(ClientWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
            .setContent("yyyy").build());

        clientWordsObserver.onNext(ClientWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
            .setContent("zzzz").build());

        clientWordsObserver.onCompleted();

    }
}
