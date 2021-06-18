package org.example.hello.world.server;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.example.hello.world.server.config.ServerConfig;
import org.example.hello.world.server.grpc.ChatServiceImpl;
import org.example.hello.world.server.grpc.GoodbyeServiceImpl;
import org.example.hello.world.server.grpc.HelloServiceImpl;
import org.example.hello.world.common.Constants;
import org.example.hello.world.common.Stopper;
import org.example.hello.world.common.utils.PropertyUtils;
import org.example.hello.world.common.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import io.grpc.ServerBuilder;

@ComponentScan(value = "org.example.hello.world")
public class Server {

    static final Logger logger = LoggerFactory.getLogger(Server.class);

    @Autowired(required = true)
    private ServerConfig serverConfig;

    @Autowired(required = true)
    HelloServiceImpl helloServiceImpl;

    @Autowired(required = true)
    GoodbyeServiceImpl goodbyeServiceImpl;

    @Autowired(required = true)
    ChatServiceImpl chatServiceImpl;

    ThreadPoolExecutor threadPoolExecutor;

    private io.grpc.Server grpcServer;

    public static void main(String[] args) {
        Thread.currentThread().setName(Constants.THREAD_NAME_SERVER);
        new SpringApplicationBuilder(Server.class).web(WebApplicationType.NONE).run(args);
   }

    @PostConstruct
    public void run() {
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    close("shutdownHook");
                }
            }));

            threadPoolExecutor = ThreadUtils.newDaemonFixedThreadPool(serverConfig.getGrpcServerThreadpoolsize(), Constants.THREAD_NAME_PREFIX_GRPC_SERVER);

            grpcServer = ServerBuilder.forPort(serverConfig.getGrpcServerPort())
                .addService(helloServiceImpl)
                .addService(goodbyeServiceImpl)
                .addService(chatServiceImpl)
                .executor(threadPoolExecutor)
                .build();

            grpcServer.start();
            logger.info("Server started!");
            grpcServer.awaitTermination();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void close(String cause) {

        try {
            // execute only once
            if(Stopper.isStopped()){
                return;
            }

            logger.info("server is stopping ..., cause : {}", cause);

            // set stop signal is true
            Stopper.stop();

            if (grpcServer != null) {
                grpcServer.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            }

            if (threadPoolExecutor != null) {
                threadPoolExecutor.shutdown();
                threadPoolExecutor.awaitTermination(5, TimeUnit.SECONDS);
            }

            try {
                //thread sleep 3 seconds for thread quietly stop
                Thread.sleep(3000L);
            }catch (Exception e){
                logger.warn("thread sleep exception ", e);
            }
        } catch (Exception e) {
            logger.error("server stop exception ", e);
            System.exit(-1);
        }
    }
    
}
