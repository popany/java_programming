package org.example.todo_mvc.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_session")
public class Session {

    @TableId(value="id", type=IdType.INPUT)
    private String id;

    @TableField(value = "user_id")
    private int userId;

    private String ip;

    @TableField(value = "last_login_time")
    private Date lastLoginTime;
}
