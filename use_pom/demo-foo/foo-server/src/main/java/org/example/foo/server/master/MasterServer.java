package org.example.foo.server.master;

import javax.annotation.PostConstruct;

import org.example.foo.common.Constants;
import org.example.foo.common.Stopper;
import org.example.foo.common.utils.PropertyUtils;
import org.example.foo.server.master.config.MasterConfig;
import org.example.foo.server.worker.WorkerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(value = "org.example.foo", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org.example.foo.server.worker.*")
    })
public class MasterServer {

    static final Logger logger = LoggerFactory.getLogger(MasterServer.class);

    @Autowired(required = true)
    private MasterConfig masterConfig;

    public static void main(String[] args) {
        Thread.currentThread().setName(Constants.THREAD_NAME_MASTER_SERVER);
        new SpringApplicationBuilder(MasterServer.class).web(WebApplicationType.NONE).run(args);
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

            while (Stopper.isRunning()) {
                Thread.sleep(masterConfig.getMasterPollingintervalseconds() * 1000);
                logger.info("master: {}", PropertyUtils.getString(Constants.NODE_NAME));
                logger.debug("master: debug");
            }
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

            logger.info("master server is stopping ..., cause : {}", cause);

            // set stop signal is true
            Stopper.stop();

            try {
                //thread sleep 3 seconds for thread quietly stop
                Thread.sleep(3000L);
            }catch (Exception e){
                logger.warn("thread sleep exception ", e);
            }
        } catch (Exception e) {
            logger.error("master server stop exception ", e);
            System.exit(-1);
        }
    }
    
}
