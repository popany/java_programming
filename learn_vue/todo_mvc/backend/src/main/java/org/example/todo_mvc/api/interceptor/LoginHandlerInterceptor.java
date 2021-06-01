package org.example.todo_mvc.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.example.todo_mvc.api.security.Authenticator;
import org.example.todo_mvc.common.Constants;
import org.example.todo_mvc.dao.entity.User;
import org.example.todo_mvc.dao.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginHandlerInterceptor.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Authenticator authenticator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String token = request.getHeader("token");
        User user = null;
        if (StringUtils.isBlank(token)){
            user = authenticator.getAuthUser(request);
            if (user == null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                logger.info("user does not exist");
                return false;
            }
        } else {
            user = userMapper.queryUserByToken(token);
            if (user == null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                logger.info("user token has expired");
                return false;
 
            }
        }
        request.setAttribute(Constants.SESSION_USER, user);
        return true;
    }
}
