package com.lyb.olinemusicserver.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName song
 */
@TableName(value ="song")
@Data
public class Song {
    private Integer id;

    private Integer singerId;

    private String name;

    private String introduction;

    private Date createTime;

    private Date updateTime;

    private String pic;

    private String lyric;

    private String url;
}