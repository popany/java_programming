package org.example.todo_mvc.api.security;

import javax.servlet.http.HttpServletRequest;

import org.example.todo_mvc.api.service.SessionService;
import org.example.todo_mvc.api.service.UserService;
import org.example.todo_mvc.dao.entity.Session;
import org.example.todo_mvc.dao.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PasswordAuthenticator implements Authenticator {

    private static final Logger logger = LoggerFactory.getLogger(PasswordAuthenticator.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Override
    public User getAuthUser(HttpServletRequest request) {
        Session session = sessionService.getSession(request);
        if (session == null) {
            logger.info("session info is null ");
            return null;
        }
        return userService.queryUser(session.getUserId());
    }
    
}
