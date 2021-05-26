package org.example.todo_mvc.api.controller;

import java.util.Map;

import org.example.todo_mvc.api.enums.Status;
import org.example.todo_mvc.api.exceptions.ApiException;
import org.example.todo_mvc.api.service.TodoService;
import org.example.todo_mvc.api.utils.Result;
import org.example.todo_mvc.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
public class TodoController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoService todoService;

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiException(Status.CREATE_TODO_ERROR)
    public Result createTodo(@RequestParam(value = "todoJson", required = true) String todoJson) {
        logger.info("create todo, todo_json: {}", todoJson);

        Map<String, Object> result = todoService.createTodo(todoJson);
        return returnDataList(result);
    }
    
    @PostMapping(value = "/update")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(Status.UPDATE_TODO_ERROR)
    public Result updateTodo(@RequestParam(value = "todoJson", required = true) String todoJson) {
        logger.info("update todo, todo_json: {}", todoJson);

        Map<String, Object> result = todoService.updateTodo(todoJson);
        return returnDataList(result);
    }

    @GetMapping(value = "/list-paging")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(Status.QUERY_TODO_LIST_PAGING_ERROR)
    public Result queryTodoListPaging(@RequestParam("pageNo") Integer pageNo,
                                      @RequestParam("pageSize") Integer pageSize) {
        logger.info("query todo list paging");

        Map<String, Object> result = checkPageParams(pageNo, pageSize);
        if (result.get(Constants.STATUS) != Status.SUCCESS) {
            return returnDataListPaging(result);
        }
        result = todoService.queryTodoListPaging(pageSize, pageNo);
        return returnDataListPaging(result);
    }

    @PostMapping(value = "/delete")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(Status.DELETE_TODO_ERROR)
    public Result deleteTodo(@RequestParam(value = "todoId", required = true) Integer todoId) {
        logger.info("delete todo, id: {}", todoId);

        Map<String, Object> result = todoService.deleteTodo(todoId);
        return returnDataList(result);
    }
}
