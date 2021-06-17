package org.example.hello.world.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
@PropertySource(value = "server.properties")
public class ClientConfig {

    @Value("${master.pollingintervalseconds:10}")
    private int masterPollingintervalseconds;
}
