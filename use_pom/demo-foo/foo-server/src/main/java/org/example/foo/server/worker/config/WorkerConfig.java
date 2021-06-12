package org.example.foo.server.worker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
@PropertySource(value = "workder.properties")
public class WorkerConfig {

    @Value("${worker.pollingintervalseconds:10}")
    private int workerPollingintervalseconds;
}
