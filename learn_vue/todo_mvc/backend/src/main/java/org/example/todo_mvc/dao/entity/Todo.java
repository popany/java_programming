package org.example.todo_mvc.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.NonNull;
import lombok.NoArgsConstructor;

@NoArgsConstructor  
@Data
@TableName("t_todo")
public class Todo {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @NonNull
    private String title;  

    private Boolean completed = false;
}
