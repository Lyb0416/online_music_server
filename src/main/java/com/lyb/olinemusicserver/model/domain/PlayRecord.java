package com.lyb.olinemusicserver.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName play_record
 */
@TableName(value ="play_record")
@Data
public class PlayRecord {
    private Long id;

    private Long userId;

    private Long songId;

    private Date createTime;
}