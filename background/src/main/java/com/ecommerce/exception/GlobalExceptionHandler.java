package com.ecommerce.exception;

import com.ecommerce.constant.enums.ResultEnum;
import com.ecommerce.model.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) // 捕获所有异常
    public Result ex(Exception ex){
        return Result.error(ResultEnum.UNKNOWN_ERROR);
    }
}
