package org.example.todo_mvc.common.utils;

import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class JSONUtils {

    private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);

    public static <T> T parseObject(String json, Class<T> clazz) {
        if (StringUtils.hasLength(json)) {
        return null;
    }

    try {
        return JSON.parseObject(json, clazz);
    } catch (Exception e) {
        logger.error("parse object exception!",e);
    }
    return null;
    }
}
