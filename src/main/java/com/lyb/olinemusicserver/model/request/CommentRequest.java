package com.lyb.olinemusicserver.model.request;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class CommentRequest {
    private Integer id;

    private Integer userId;

    private Integer songId;

    private Integer songListId;

    private String content;

    private Date createTime;

    private Byte nowType;

    private Integer up;//点赞
}