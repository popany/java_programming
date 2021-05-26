package org.example.todo_mvc.api.enums;

public enum Status {

    SUCCESS(0, "success"),
    INTERNAL_SERVER_ERROR_ARGS(10000, "Internal Server Error: {0}"),
    REQUEST_PARAMS_NOT_VALID_ERROR(10001, "request parameter {0} is not valid"),
    CREATE_TODO_ERROR(10011,"create todo error"),
    UPDATE_TODO_ERROR(10012,"create todo error"),
    DELETE_TODO_ERROR(10013,"delete todo error"),
    QUERY_TODO_LIST_PAGING_ERROR(10014, "query todo list paging error");

    private final int code;
    private final String msg;
    
    private Status(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
