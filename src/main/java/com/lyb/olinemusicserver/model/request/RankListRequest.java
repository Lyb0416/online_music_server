package com.lyb.olinemusicserver.model.request;

import lombok.Data;

@Data
public class RankListRequest {
    private Long id;

    /** 歌单id */
    private Long songListId;

    /** 用户id */
    private Long consumerId;

    /** 用户评分 */
    private Integer score;
}
