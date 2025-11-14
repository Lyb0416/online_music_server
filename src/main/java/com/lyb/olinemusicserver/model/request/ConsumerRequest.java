package com.lyb.olinemusicserver.model.request;

import lombok.Data;

import java.sql.Date;

@Data
public class ConsumerRequest {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户姓名
     */
    private String username;

    /**
     *  因为会用到用户旧密码 这无所谓的对应即可
     */
    private String oldPassword;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 电话
     */
    private String phoneNum;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户出生年月日
     */
    private Date birth;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 籍贯
     */
    private String location;

    /**
     * 用户头像路径
     */
    private String avator;

    /**
     * 创建时间
     */
    private Date createTime;
}
