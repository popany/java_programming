package org.example.todo_mvc.api.exceptions;

import org.example.todo_mvc.api.enums.Status;
import org.example.todo_mvc.api.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

@ControllerAdvice
@ResponseBody
public class ApiExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e, HandlerMethod hm) {
        ApiException ce = hm.getMethodAnnotation(ApiException.class);
        if (ce == null) {
            logger.error(e.getMessage(), e);
            return Result.errorWithArgs(Status.INTERNAL_SERVER_ERROR_ARGS, e.getMessage());
        }
        Status st = ce.value();
        logger.error(st.getMsg(), e);
        return Result.error(st);
    }

}
