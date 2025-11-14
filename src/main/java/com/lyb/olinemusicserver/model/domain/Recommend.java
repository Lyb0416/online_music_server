package com.lyb.olinemusicserver.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName recommend
 */
@TableName(value ="recommend")
@Data
public class Recommend {
    private Long id;

    private Long userId;

    private Long songId;

    private BigDecimal recommendLevel;

    private Date createTime;
}