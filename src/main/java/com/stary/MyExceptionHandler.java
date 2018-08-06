package com.stary;

import com.stary.model.MyResponse;
import com.stary.model.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，未捕获的异常统一返回未知错误
 */
@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(Exception.class)
    private MyResponse handlerException(Exception e) {
        log.error("未捕获的异常", e);
        return MyResponse.createResponse(ResponseEnum.UNKNOWN_ERROR);
    }
}