package org.example.todo_mvc.common.utils;

import java.util.TimeZone;

import com.alibaba.fastjson.JSON;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONUtils {

    private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);

    public static <T> T parseObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
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
