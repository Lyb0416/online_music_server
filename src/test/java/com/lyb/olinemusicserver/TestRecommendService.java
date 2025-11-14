package com.lyb.olinemusicserver;

import com.lyb.olinemusicserver.service.RecommendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class TestRecommendService {

    @Autowired
    private RecommendService recommendService;

    @Test
    public void testUpdatePersonalRecInfo(){
        Map<Integer, Integer[]> user2songRecMatrix = new HashMap<>();
        Integer[] songIds = new Integer[]{1, 2, 3, 5, 8};
        user2songRecMatrix.put(1, songIds);
        recommendService.updatePersonalRecInfo(user2songRecMatrix);
    }
}
