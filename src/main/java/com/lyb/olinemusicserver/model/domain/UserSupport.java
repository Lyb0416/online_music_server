package com.lyb.olinemusicserver.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName user_support
 */
@TableName(value ="user_support")
@Data
public class UserSupport {
    private Integer id;

    private Integer commentId;

    private String userId;
}