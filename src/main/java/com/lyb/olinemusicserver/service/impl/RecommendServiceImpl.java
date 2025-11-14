package com.lyb.olinemusicserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.olinemusicserver.model.domain.Recommend;
import com.lyb.olinemusicserver.service.RecommendService;
import com.lyb.olinemusicserver.mapper.RecommendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
* @author LENOVO
* @description 针对表【recommend】的数据库操作Service实现
* @createDate 2025-11-12 15:45:39
*/
@Service
public class RecommendServiceImpl extends ServiceImpl<RecommendMapper, Recommend>
    implements RecommendService{

    @Autowired
    private RecommendMapper recommendMapper;

    /**
     * @Description: 向推荐表中写入推荐数据
     * @param: user2songRecMatrix 其中 key 为用户id，value 为推荐给该用户的歌曲id数组。
     */
    @Transactional
    @Override
    public void updatePersonalRecInfo(Map<Integer, Integer[]> user2songRecMatrix) {
        user2songRecMatrix.forEach((userId, songIds) -> {
            recommendMapper.deleteByUserId(userId);
            recommendMapper.batchInsert(userId, songIds);
        });
    }
}




