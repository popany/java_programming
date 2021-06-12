package org.example.foo.server.master;

import org.example.foo.common.Constants;
import org.example.foo.common.utils.PropertyUtils;
import org.example.foo.server.master.config.MasterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MasterServer {

    static final Logger logger = LoggerFactory.getLogger(MasterServer.class);

    @Autowired
    private static MasterConfig masterConfig;

    public static void main(String[] args) {
        try {
            while (true) {
                Thread.sleep(masterConfig.getMasterPollingintervalseconds() * 1000);
                logger.info("master: {}", PropertyUtils.getString(Constants.NODE_NAME));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    
}
