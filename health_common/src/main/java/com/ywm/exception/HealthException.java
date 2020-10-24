package com.ywm.exception;

/**
 * @author: YEWENMAO
 * @data: 2020/10/24 13:47
 */

/**
 * 自定义异常:
 *      友好提示
 *      区分系统与业务异常
 *      终止已经不符合业务逻辑的代码
 */
public class HealthException extends RuntimeException {
    public HealthException(String message) {
            super(message);
    }
}
