package com.lyb.olinemusicserver;

import com.lyb.olinemusicserver.model.request.RankListRequest;
import com.lyb.olinemusicserver.service.RankListService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class TestRankListService {

    @Autowired
    private RankListService rankListService;

    /** 测试歌单评价 Service 接口 */
    @Test
    public void testAddRank(){
        /** 构造歌单评价请求参数对象 */
        RankListRequest rankListRequest = new RankListRequest();
        rankListRequest.setSongListId(1L);
        rankListRequest.setConsumerId(63L);
        rankListRequest.setScore(8);
        log.info("测试歌单评价：{}", rankListService.addRank(rankListRequest));
    }

    /** 测试获取歌单评分接口 */
    @Test
    public void testRankOfSongListId(){
        log.info("测试获取歌单评分：{}", rankListService.rankOfSongListId(5L));
    }
}
