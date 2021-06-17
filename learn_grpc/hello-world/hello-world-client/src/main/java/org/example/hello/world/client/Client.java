package org.example.hello.world.client;

import javax.annotation.PostConstruct;

import org.example.hello.world.client.config.ClientConfig;
import org.example.hello.world.common.Constants;
import org.example.hello.world.common.Stopper;
import org.example.hello.world.common.utils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(value = "org.example.hello.world", excludeFilters = {
    })
public class Client {

    static final Logger logger = LoggerFactory.getLogger(Client.class);

    @Autowired(required = true)
    private ClientConfig clientConfig;

    public static void main(String[] args) {
        Thread.currentThread().setName(Constants.THREAD_NAME_SERVER);
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

            logger.info("client: info");
            logger.debug("client: debug");
                
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
