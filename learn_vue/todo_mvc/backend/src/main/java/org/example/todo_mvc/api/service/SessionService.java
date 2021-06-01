package org.example.todo_mvc.api.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.example.todo_mvc.api.controller.BaseController;
import org.example.todo_mvc.common.Constants;
import org.example.todo_mvc.dao.entity.Session;
import org.example.todo_mvc.dao.mapper.SessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    @Autowired
    SessionMapper sessionMapper;

    public Session getSession(HttpServletRequest request)  {
        String sessionId = request.getHeader(Constants.SESSION_ID);

        if (StringUtils.isBlank(sessionId)) {
            Cookie cookie = getCookie(request, Constants.SESSION_ID);

            if (cookie != null) {
                sessionId = cookie.getValue();
            }
        }

        if (StringUtils.isBlank(sessionId)) {
            return null;
        }

        String ip = BaseController.getClientIpAddress(request);
        logger.debug("get session: {}, ip: {}", sessionId, ip);

        return sessionMapper.selectById(sessionId);
    }
    
}
