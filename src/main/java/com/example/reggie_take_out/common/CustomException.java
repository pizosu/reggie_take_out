package com.example.reggie_take_out.common;

/**
 * @Author: Su
 * @Date: 2022-11-10-15:49
 * @Description:
 */

/**
 * @Description 自定义业务异常处理
 */
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
}
