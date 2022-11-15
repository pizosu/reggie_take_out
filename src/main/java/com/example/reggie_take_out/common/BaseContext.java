package com.example.reggie_take_out.common;

/**
 * 基于ThreadLocal封装工具类，用于保存和获取当前登录用户id
 * @Author: Su
 * @Date: 2022-11-10-11:01
 * @Description:
 */

public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new InheritableThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

}
