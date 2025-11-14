package com.lyb.olinemusicserver.model.request;

import lombok.Data;

@Data
public class SongListRequest {
    private Integer id;
    /** 歌单标题 */
    private String title;
    /** 歌单图片 */
    private String pic;
    /** 歌单风格 */
    private String style;
    /** 歌单介绍 */
    private String introduction;
}
