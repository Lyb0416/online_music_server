package com.lyb.olinemusicserver.common;

import lombok.Data;

/**
 * 公共Result对象，可由该对象生成返回的JSON数据
 */
@Data
public class R {
    // Http响应的状态码
    private int code;
    // 消息文本
    private String message;
    // 消息类型
    private String type;
    // 处理是否成功
    private Boolean success;
    // 响应数据
    private Object data;

    /**
     * 处理成功，无消息、无返回数据
     * @return
     */
    public static R ok() {
        R r = new R();
        r.setCode(200);
        r.setSuccess(true);
        r.setType("success");
        r.setData(null);
        return r;
    }

    /**
     * 处理成功，有消息，无返回数据
     * @param message   消息文本
     * @return  result对象
     */
    public static R success(String message) {
        R r = new R();
        r.setCode(200);
        r.setMessage(message);
        r.setSuccess(true);
        r.setType("success");
        r.setData(null);
        return r;
    }

    /**
     * 处理成功，有消息，有返回数据
     * @param message   消息文本
     * @param data  返回的数据
     * @return  result对象
     */
    public static R success(String message, Object data) {
        R r = success(message);
        r.setData(data);
        return r;
    }

    /**
     * 处理异常，返回警告消息
     * @param message   消息文本
     * @return  result对象
     */
    public static R warning(String message) {
        R r = error(message);
        r.setType("warning");
        return r;
    }

    /**
     * 发生错误，返回报错消息
     * @param message   消息文本
     * @return  result对象
     */
    public static R error(String message) {
        R r = success(message);
        r.setSuccess(false);
        r.setType("error");
        return r;
    }

    /**
     * 服务端发生致命错误，返回报错消息
     * @param message   消息文本
     * @return  result对象
     */
    public static R fatal(String message) {
        R r = error(message);
        r.setCode(500);
        return r;
    }
}
