package com.lyb.olinemusicserver.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName banner
 */
@TableName(value ="banner")
@Data
public class Banner {
    private Integer id;

    private String pic;
}