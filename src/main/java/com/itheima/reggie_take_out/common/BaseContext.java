package com.itheima.reggie_take_out.common;

/**
 * 基于ThreadLocal封装工具类，用于保存和获取当前id
 */
public class BaseContext {
    public static ThreadLocal<Long>threadLocal = new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
