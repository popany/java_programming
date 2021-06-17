package org.example.hello.world.common;

public final class Constants {
    private Constants() {
        throw new IllegalStateException("Constants class");
    }

    public static final String THREAD_NAME_SERVER = "Server";
    public static final String THREAD_NAME_CLIENT = "Client";
    public static final String COMMON_PROPERTIES_PATH = "/common.properties";
    public static final String NODE_NAME = "node.name";
}