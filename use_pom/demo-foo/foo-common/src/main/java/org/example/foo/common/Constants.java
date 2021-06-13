package org.example.foo.common;

public final class Constants {
    private Constants() {
        throw new IllegalStateException("Constants class");
    }

    public static final String THREAD_NAME_MASTER_SERVER = "Master-Server";
    public static final String THREAD_NAME_WORKER_SERVER = "Worker-Server";
    public static final String COMMON_PROPERTIES_PATH = "/common.properties";
    public static final String NODE_NAME = "node.name";
}