package org.example.foo.server.worker;

import javax.annotation.PostConstruct;

import org.example.foo.common.Constants;
import org.example.foo.common.Stopper;
import org.example.foo.common.utils.PropertyUtils;
import org.example.foo.server.worker.config.WorkerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(value = "org.example.foo", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "org.example.foo.server.master.*")
    })
public class WorkerServer {
    
    static final Logger logger = LoggerFactory.getLogger(WorkerServer.class);

    @Autowired
    private WorkerConfig workerConfig;

    public static void main(String[] args) {
        Thread.currentThread().setName(Constants.THREAD_NAME_WORKER_SERVER);
        new SpringApplicationBuilder(WorkerServer.class).web(WebApplicationType.NONE).run(args);
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
                Thread.sleep(workerConfig.getWorkerPollingintervalseconds() * 1000);
                logger.info("worker: {}", PropertyUtils.getString(Constants.NODE_NAME));
                logger.debug("worker: debug");
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

            logger.info("worker server is stopping ..., cause : {}", cause);

            // set stop signal is true
            Stopper.stop();

            try {
                //thread sleep 3 seconds for thread quietly stop
                Thread.sleep(3000L);
            }catch (Exception e){
                logger.warn("thread sleep exception ", e);
            }
        } catch (Exception e) {
            logger.error("worker server stop exception ", e);
            System.exit(-1);
        }
    }
    
}
