package com.lyb.olinemusicserver;

import com.lyb.olinemusicserver.algorithm.RecommendTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: 测试推荐歌曲数据
 */
@SpringBootTest
public class TestRecommendTask {

    @Autowired
    RecommendTask recommendTask;

    @Test
    public void testRecommend(){
        recommendTask.recommend();
    }
}
