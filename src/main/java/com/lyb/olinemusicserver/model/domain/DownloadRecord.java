package com.lyb.olinemusicserver.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName download_record
 */
@TableName(value ="download_record")
@Data
public class DownloadRecord {
    private Long id;

    private Long userId;

    private Long songId;

    private Date createTime;
}