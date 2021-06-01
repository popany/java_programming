package org.example.todo_mvc.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.example.todo_mvc.dao.entity.User;

public interface UserMapper extends BaseMapper<User> {

    User queryUserByToken(@Param("token") String token);

}
