package org.example.hello.world.common;

public final class Constants {
    private Constants() {
        throw new IllegalStateException("Constants class");
    }

    public static final String THREAD_NAME_SERVER = "server";
    public static final String THREAD_NAME_PREFIX_GRPC_SERVER = "grpc-server-thread";
    public static final String THREAD_NAME_PREFIX_GRPC_CHAT_SERVICE = "grpc-chat-service-thread";

    public static final String THREAD_NAME_CLIENT = "client";
    public static final String THREAD_NAME_PREFIX_GRPC_CLIENT = "grpc-client-thread";

    public static final String COMMON_PROPERTIES_PATH = "/common.properties";
}