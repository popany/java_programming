package org.example.todo_mvc.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import org.example.todo_mvc.common.enums.UserType;

import lombok.Data;

@Data
@TableName("t_user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "user_password")
    private String userPassword;

    @TableField(value = "user_type")
    private UserType userType;

    private String email;

    private String phone;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;
}
