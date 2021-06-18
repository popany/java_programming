package org.example.hello.world.common.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadUtils {
    public static void sleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (final InterruptedException ignore) {}
    }

    private static ThreadFactory namedThreadFactory(String prefix) {
        return new ThreadFactoryBuilder()
            .setDaemon(true)
            .setNameFormat(prefix + "-%d")
            .build();
    }

    public static ThreadPoolExecutor newDaemonFixedThreadPool(int nThreads , String prefix){
        ThreadFactory threadFactory = namedThreadFactory(prefix);
        return ((ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads, threadFactory));
    }
}
