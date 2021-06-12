package org.example.foo.server.worker;

import org.example.foo.common.Constants;
import org.example.foo.common.utils.PropertyUtils;
import org.example.foo.server.worker.config.WorkerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkerServer {
    
    static final Logger logger = LoggerFactory.getLogger(WorkerServer.class);

    @Autowired
    private static WorkerConfig workerConfig;

    public static void main(String[] args) {
        try {
            while (true) {
                Thread.sleep(workerConfig.getWorkerPollingintervalseconds() * 1000);
                logger.info("worker: {}", PropertyUtils.getString(Constants.NODE_NAME));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    
}
