package com.lyb.olinemusicserver.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName rank_list
 */
@TableName(value ="rank_list")
@Data
public class RankList {
    private Long id;

    private Long songListId;

    private Long consumerId;

    private Integer score;
}