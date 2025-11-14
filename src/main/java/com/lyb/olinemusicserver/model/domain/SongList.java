package com.lyb.olinemusicserver.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @TableName song_list
 */
@TableName(value = "song_list")
@Data
public class SongList {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 歌单标题 */
    private String title;
    /** 歌单图片 */
    private String pic;
    /** 歌单风格 */
    private String style;
    /** 歌单介绍 */
    private String introduction;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}