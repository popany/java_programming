package org.example.hello.world.client;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.example.hello.world.client.config.ClientConfig;
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
import org.springframework.context.annotation.FilterType;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@ComponentScan(value = "org.example.hello.world", excludeFilters = {
    })
public class Client {

    static final Logger logger = LoggerFactory.getLogger(Client.class);

    @Autowired(required = true)
    private ClientConfig clientConfig;

    private ThreadPoolExecutor threadPoolExecutor;

    public static void main(String[] args) {
        Thread.currentThread().setName(Constants.THREAD_NAME_CLIENT);
        new SpringApplicationBuilder(Client.class).web(WebApplicationType.NONE).run(args);
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

            ManagedChannel channel = ManagedChannelBuilder.forAddress(clientConfig.getGrpcServerHost(), clientConfig.getGrpcServerPort())
                .usePlaintext()
                .build();

            HelloClient helloClient = new HelloClient(channel);
            helloClient.sayHello();
            helloClient.sayHelloAgain();

            GoodbyeClient goodbyeClient = new GoodbyeClient(channel);
            goodbyeClient.sayGoodbye();
            goodbyeClient.sayGoodbyeAgain();

            threadPoolExecutor = ThreadUtils.newDaemonFixedThreadPool(clientConfig.getGrpcClientThreadpoolsize(), Constants.THREAD_NAME_PREFIX_GRPC_CLIENT);

            ChatClient chatClient = new ChatClient(channel, threadPoolExecutor);
            chatClient.blockingGreet();
            chatClient.blockingListen();

            chatClient.futureGreet();

            chatClient.asyncGreet();
            chatClient.asyncListen();
            chatClient.asyncSpeak();
            chatClient.asyncTalk();
            ThreadUtils.sleep(3000);

            channel.shutdown();

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

            logger.info("client is stopping ..., cause : {}", cause);

            // set stop signal is true
            Stopper.stop();

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
            logger.error("client stop exception ", e);
            System.exit(-1);
        }
    }
    
}
