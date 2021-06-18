package org.example.hello.world.server.grpc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;

import org.example.hello.world.common.Constants;
import org.example.hello.world.common.utils.ThreadUtils;
import org.example.hello.world.grpc.ClientWords;
import org.example.hello.world.grpc.ServerWords;
import org.example.hello.world.grpc.ChatServiceGrpc.ChatServiceImplBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.stub.StreamObserver;

public class ChatServiceImpl extends ChatServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    private ThreadPoolExecutor threadPoolExecutor;

    public ChatServiceImpl(int threadPoolSize) {
        threadPoolExecutor = ThreadUtils.newDaemonFixedThreadPool(threadPoolSize, Constants.THREAD_NAME_PREFIX_GRPC_CHAT_SERVICE);
    }

    @Override
    public void greet(ClientWords clientWords, StreamObserver<ServerWords> serverWordsObserver) {
        threadPoolExecutor.submit(new Callable<Void>() {
            @Override
            public Void call() {
                logger.info("greet received from client: {}", clientWords);

                serverWordsObserver.onNext(ServerWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
                    .setContent("replay> " + clientWords.getContent()).build());
                serverWordsObserver.onCompleted();

                return null;
            }
        });
    }

    @Override
    public void listen(ClientWords clientWords, StreamObserver<ServerWords> serverWordsObserver) {
        threadPoolExecutor.submit(new Callable<Void>() {
            @Override
            public Void call() {
                logger.info("listen received from client: {}", clientWords);

                serverWordsObserver.onNext(ServerWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
                    .setContent("aaaa").build());
                
                serverWordsObserver.onNext(ServerWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
                    .setContent("bbbb").build());

                serverWordsObserver.onNext(ServerWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
                    .setContent("cccc").build());

                serverWordsObserver.onCompleted();

                return null;
            }
        });
    }

    @Override
    public StreamObserver<ClientWords> speak(final StreamObserver<ServerWords> ServerWordsObserver) {

        return new StreamObserver<ClientWords>() {

            @Override
            public void onNext(ClientWords clientWords) {
                threadPoolExecutor.submit(new Callable<Void>() {
                    @Override
                    public Void call() {
                        logger.info("speak received from client: {}", clientWords);

                        return null;
                    }
                });
            }

            @Override
            public void onError(Throwable t) {
                logger.error("error: {}", t.getMessage(), t);
            }

            @Override
            public void onCompleted() {
                threadPoolExecutor.submit(new Callable<Void>() {
                    @Override
                    public Void call() {
                        ServerWordsObserver.onNext(ServerWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
                            .setContent("ok").build());

                        ServerWordsObserver.onCompleted();

                        return null;
                    }
                });
            }
        };
    }

    @Override
    public StreamObserver<ClientWords> talk(final StreamObserver<ServerWords> serverWordsObserver) {

        return new StreamObserver<ClientWords>() {

            @Override
            public void onNext(ClientWords clientWords) {
                threadPoolExecutor.submit(new Callable<Void>() {
                    @Override
                    public Void call() {
                        logger.info("talk received from client: {}", clientWords);

                        serverWordsObserver.onNext(ServerWords.newBuilder().setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()))
                            .setContent("replay> " + clientWords.getContent()).build());

                        return null;
                    }
                });
            }

            @Override
            public void onError(Throwable t) {
                logger.error("error: {}", t.getMessage(), t);
            }

            @Override
            public void onCompleted() {
                threadPoolExecutor.submit(new Callable<Void>() {
                    @Override
                    public Void call() {
                        serverWordsObserver.onCompleted();

                        return null;
                    }
                });
            }
        };
    }

}
