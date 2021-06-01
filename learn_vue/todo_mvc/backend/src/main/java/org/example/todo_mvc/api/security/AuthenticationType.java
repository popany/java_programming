package org.example.todo_mvc.api.security;

public enum AuthenticationType {

    PASSWORD(0, "verify via user name and password");

    AuthenticationType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final int code;
    private final String desc;
}
