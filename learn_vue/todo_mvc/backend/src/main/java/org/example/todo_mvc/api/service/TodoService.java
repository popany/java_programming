package org.example.todo_mvc.api.service;

import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.example.todo_mvc.api.enums.Status;
import org.example.todo_mvc.api.utils.PageInfo;
import org.example.todo_mvc.common.Constants;
import org.example.todo_mvc.common.utils.JSONUtils;
import org.example.todo_mvc.dao.entity.Todo;
import org.example.todo_mvc.dao.mapper.TodoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService extends BaseService {

    @Autowired
    TodoMapper todoMapper;

    public Map<String, Object> createTodo(String todoJson) {
        Map<String, Object> result = new HashMap<>();

        Todo todo = JSONUtils.parseObject(todoJson, Todo.class);
        if (todoMapper.insert(todo) > 0) {
            putMsg(result, Status.SUCCESS);
        } else {
            putMsg(result, Status.CREATE_TODO_ERROR);
        }

        return result;
    }

    public Map<String, Object> updateTodo(String todoJson) {
        Map<String, Object> result = new HashMap<>();

        Todo todo = JSONUtils.parseObject(todoJson, Todo.class);
        if (todoMapper.updateById(todo) > 0) {
            putMsg(result, Status.SUCCESS);
        } else {
            putMsg(result, Status.UPDATE_TODO_ERROR);
        }

        return result;
    }

    public Map<String, Object> deleteTodo(Integer id) {
        Map<String, Object> result = new HashMap<>();

        if (todoMapper.deleteById(id) > 0) {
            putMsg(result, Status.SUCCESS);
        } else {
            putMsg(result, Status.DELETE_TODO_ERROR);
        }

        return result;
    }

    public Map<String, Object> queryTodoListPaging(Integer pageSize, Integer pageNo) {
        Map<String, Object> result = new HashMap<>();
        Page<Todo> page = new Page<>(pageNo, pageSize);

        QueryWrapper<Todo> queryWrapper = new QueryWrapper<Todo>();
        queryWrapper.orderByDesc("create_time");
        IPage<Todo> todoIPage = todoMapper.selectPage(page, queryWrapper);

        PageInfo<Todo> pageInfo = new PageInfo<>(pageNo, pageSize);
        pageInfo.setTotalCount((int)todoIPage.getTotal());
        pageInfo.setLists(todoIPage.getRecords());
        result.put(Constants.DATA_LIST, pageInfo);
        putMsg(result, Status.SUCCESS);

        return result;
    }
}
