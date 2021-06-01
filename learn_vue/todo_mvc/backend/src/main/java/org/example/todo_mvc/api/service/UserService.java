package org.example.todo_mvc.api.service;

import org.example.todo_mvc.dao.entity.User;
import org.example.todo_mvc.dao.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserMapper userMapper;

    public User queryUser(int id) {
        return userMapper.selectById(id);
    }
    
}
