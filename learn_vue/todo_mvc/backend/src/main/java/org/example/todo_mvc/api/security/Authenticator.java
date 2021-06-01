package org.example.todo_mvc.api.security;

import javax.servlet.http.HttpServletRequest;

import org.example.todo_mvc.dao.entity.User;

public interface Authenticator {
    User getAuthUser(HttpServletRequest request);
}
