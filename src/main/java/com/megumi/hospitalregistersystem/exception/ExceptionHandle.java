package com.megumi.hospitalregistersystem.exception;

import cn.hutool.core.util.StrUtil;
import com.megumi.hospitalregistersystem.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler(value = Exception.class)
    public Result handlerException(Exception ex){
        //如果出现了相关的异常，就会调用该方法
        log.error("系统错误",ex);
        return Result.error("系统错误");
    }
    @ExceptionHandler(value = serviceException.class)
    public Result servicehandlerException(serviceException ex){
        //如果出现了相关的异常，就会调用该方法
        log.error("业务异常",ex);
        if(!StrUtil.isBlank(ex.getCode())){
            return Result.error(ex.getCode(),ex.getMessage());
        }
        return Result.error(ex.getMessage());
    }
}
