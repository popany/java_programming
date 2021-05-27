package org.example.todo_mvc.api.utils;

import java.text.MessageFormat;

import org.example.todo_mvc.api.enums.Status;

public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

    private Result(Status status) {
        if (status != null) {
            this.code = status.getCode();
            this.msg = status.getMsg();
        }
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public Result() {
    }

    public static Result error(Status status) {
        return new Result(status);
    }

    public static Result errorWithArgs(Status status, Object... args) {
        return new Result(status.getCode(), MessageFormat.format(status.getMsg(), args));
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    } 
}
