package org.example.foo.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Starter {
    private static final Logger logger = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) {
        Starter starter = new Starter();
        starter.start();
    }

    private void start() {
        try {
            ;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
