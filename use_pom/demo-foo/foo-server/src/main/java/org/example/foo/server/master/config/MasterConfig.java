package org.example.foo.server.master.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
@PropertySource(value = "master.properties")
public class MasterConfig {

    @Value("${master.pollingintervalseconds:1}")
    private int masterPollingintervalseconds;
}
