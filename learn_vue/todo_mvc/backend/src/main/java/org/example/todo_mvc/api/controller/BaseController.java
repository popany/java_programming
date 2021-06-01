package org.example.todo_mvc.api.controller;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.example.todo_mvc.api.enums.Status;
import org.example.todo_mvc.api.utils.PageInfo;
import org.example.todo_mvc.api.utils.Result;
import org.example.todo_mvc.common.Constants;

public class BaseController {

    public Map<String, Object> checkPageParams(int pageNo, int pageSize) {
        Map<String, Object> result = new HashMap<>(2);
        Status resultEnum = Status.SUCCESS;
        String msg = Status.SUCCESS.getMsg();
        if (pageNo <= 0) {
            resultEnum = Status.REQUEST_PARAMS_NOT_VALID_ERROR;
            msg = MessageFormat.format(Status.REQUEST_PARAMS_NOT_VALID_ERROR.getMsg(), Constants.PAGE_NUMBER);
        } else if (pageSize <= 0) {
            resultEnum = Status.REQUEST_PARAMS_NOT_VALID_ERROR;
            msg = MessageFormat.format(Status.REQUEST_PARAMS_NOT_VALID_ERROR.getMsg(), Constants.PAGE_SIZE);
        }
        result.put(Constants.STATUS, resultEnum);
        result.put(Constants.MSG, msg);
        return result;
    }


    public Result returnDataList(Map<String, Object> result) {
        Status status = (Status) result.get(Constants.STATUS);
        if (status == Status.SUCCESS) {
            String msg = Status.SUCCESS.getMsg();
            Object datalist = result.get(Constants.DATA_LIST);
            return success(msg, datalist);
        } else {
            Integer code = status.getCode();
            String msg = (String) result.get(Constants.MSG);
            return error(code, msg);
        }
    }

    public Result returnDataListPaging(Map<String, Object> result) {
        Status status = (Status) result.get(Constants.STATUS);
        if (status == Status.SUCCESS) {
            result.put(Constants.MSG, Status.SUCCESS.getMsg());
            PageInfo pageInfo = (PageInfo) result.get(Constants.DATA_LIST);
            return success(pageInfo.getLists(), pageInfo.getCurrentPage(), pageInfo.getTotalCount(), pageInfo.getTotalPage());
        } else {
            Integer code = status.getCode();
            String msg = (String) result.get(Constants.MSG);
            return error(code, msg);
        }
    }

    public Result success(String msg, Object list) {
        return getResult(msg, list);
    }

    public Result success(Object totalList, Integer currentPage, Integer total, Integer totalPage) {
        Result result = new Result();
        result.setCode(Status.SUCCESS.getCode());
        result.setMsg(Status.SUCCESS.getMsg());

        Map<String, Object> map = new HashMap<>(4);
        map.put(Constants.TOTAL_LIST, totalList);
        map.put(Constants.CURRENT_PAGE, currentPage);
        map.put(Constants.TOTAL_PAGE, totalPage);
        map.put(Constants.TOTAL, total);
        result.setData(map);
        return result;
    }

    public Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    private Result getResult(String msg, Object list) {
        Result result = new Result();
        result.setCode(Status.SUCCESS.getCode());
        result.setMsg(msg);

        result.setData(list);
        return result;
    }
    
    public static String getClientIpAddress(HttpServletRequest request) {
        String clientIp = request.getHeader(Constants.HTTP_X_FORWARDED_FOR);

        if (StringUtils.isNotEmpty(clientIp) && !clientIp.equalsIgnoreCase(Constants.HTTP_HEADER_UNKNOWN)) {
            int index = clientIp.indexOf(Constants.COMMA);
            if (index != -1) {
                return clientIp.substring(0, index);
            } else {
                return clientIp;
            }
        }

        clientIp = request.getHeader(Constants.HTTP_X_REAL_IP);
        if (StringUtils.isNotEmpty(clientIp) && !clientIp.equalsIgnoreCase(Constants.HTTP_HEADER_UNKNOWN)) {
            return clientIp;
        }

        return request.getRemoteAddr();
    }
}
