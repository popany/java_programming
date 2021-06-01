package org.example.todo_mvc.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum UserType {

    ADMIN_USER(0, "admin user"),
    GENERAL_USER(1, "general user");


    UserType(int code, String descp){
        this.code = code;
        this.descp = descp;
    }

    @EnumValue
    private final int code;
    private final String descp;

    public int getCode() {
        return code;
    }

    public String getDescp() {
        return descp;
    }
}
