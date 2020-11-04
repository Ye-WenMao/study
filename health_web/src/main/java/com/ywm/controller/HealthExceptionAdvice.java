package com.ywm.controller;

import com.ywm.entity.Result;
import com.ywm.exception.HealthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: YEWENMAO
 * @data: 2020/10/24 13:54
 */

/**
 * 全局处理异常:
 *      ExceptionHandler 获取的异常 异常的范围从小到大，类似于try catch中的catch
 *      与前端约定好的，返回的都是json数据
 */
@RestControllerAdvice
public class HealthExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(HealthExceptionAdvice.class);

    /*
        自定义抛出的异常处理
     */
    @ExceptionHandler(HealthException.class)
    public Result handleHealthException(HealthException he) {
        return new Result(false, he.getMessage());
    }

    /*
        所有的未知异常
     */
    @ExceptionHandler(Exception.class)
    public Result handException(Exception e) {
        log.error("发生了异常", e);
        return new Result(false, "发生未知的错误,请联系管理员");
    }

    /**
     * 密码错误
     * @param he
     * @return
     */
    @ExceptionHandler(BadCredentialsException.class)
    public Result handBadCredentialsException(BadCredentialsException he){
        return handleUserPassword();
    }

    /**
     * 用户名不存在
     * @param he
     * @return
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public Result handInternalAuthenticationServiceException(InternalAuthenticationServiceException he){
        return handleUserPassword();
    }

    private Result handleUserPassword(){
        return new Result(false, "用户名或密码错误");
    }

    /**
     * 用户名不存在
     * @param he
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result handAccessDeniedException(AccessDeniedException he){
        return new Result(false, "没有权限");
    }
}
