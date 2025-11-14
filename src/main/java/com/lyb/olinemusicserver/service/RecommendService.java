package com.lyb.olinemusicserver.service;

import com.lyb.olinemusicserver.model.domain.Recommend;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Description: 推荐数据业务处理类
 * @Author: yang_yong
 */
public interface RecommendService extends IService<Recommend> {

    /**
     * @Description: 向推荐表中写入推荐数据
     * @param: user2songRecMatrix 其中 key 为用户id，value 为推荐给该用户的歌曲id数组。
     **/
    void updatePersonalRecInfo(Map<Integer, Integer[]> user2songRecMatrix);
}
