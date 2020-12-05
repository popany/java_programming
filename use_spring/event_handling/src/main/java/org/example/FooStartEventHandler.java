package org.example;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

public class FooStartEventHandler implements ApplicationListener<ContextStartedEvent> {
    public void onApplicationEvent(ContextStartedEvent event) {
        System.out.println("ContextStartedEvent Received");
    }
}
