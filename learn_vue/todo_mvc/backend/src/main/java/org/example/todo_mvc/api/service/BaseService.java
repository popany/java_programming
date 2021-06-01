package org.example.todo_mvc.api.service;

import java.text.MessageFormat;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.example.todo_mvc.api.enums.Status;
import org.example.todo_mvc.common.Constants;

public abstract class BaseService {

    protected void putMsg(Map<String, Object> result, Status status, Object... statusParams) {
        result.put(Constants.STATUS, status);
        if (statusParams != null && statusParams.length > 0) {
            result.put(Constants.MSG, MessageFormat.format(status.getMsg(), statusParams));
        } else {
            result.put(Constants.MSG, status.getMsg());
        }
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (StringUtils.isNotEmpty(name) && name.equalsIgnoreCase(cookie.getName())) {
                    return cookie;
                }
            }
        }

        return null;
    }
}
