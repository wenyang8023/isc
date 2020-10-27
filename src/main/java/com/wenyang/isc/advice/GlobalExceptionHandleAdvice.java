package com.wenyang.isc.advice;

import com.alibaba.fastjson.JSON;
import com.wenyang.isc.bean.Result;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author wenyang
 * @date 2019/07/24
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandleAdvice extends ResponseEntityExceptionHandler {

    /**
     * 处理异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    Result handleException(HttpServletRequest request, RuntimeException ex) {
        log.error("业务异常，请求参数={}，error={}", JSON.toJSONString(request.getParameterMap()), ExceptionUtils.getStackTrace(ex));
        return Result.error(ex.getMessage());
    }

}
