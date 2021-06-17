package org.example.hello.world.server;

import javax.annotation.PostConstruct;

import org.example.hello.world.server.config.ServerConfig;
import org.example.hello.world.server.grpc.GoodbyeServiceImpl;
import org.example.hello.world.server.grpc.HelloServiceImpl;
import org.example.hello.world.common.Constants;
import org.example.hello.world.common.Stopper;
import org.example.hello.world.common.utils.PropertyUtils;
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

            grpcServer = ServerBuilder.forPort(serverConfig.getGrpcServerPort())
                .addService(new HelloServiceImpl())
                .addService(new GoodbyeServiceImpl())
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
            grpcServer.shutdown();

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
