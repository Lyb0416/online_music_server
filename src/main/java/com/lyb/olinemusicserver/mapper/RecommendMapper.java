package com.lyb.olinemusicserver.mapper;

import com.lyb.olinemusicserver.model.domain.Recommend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author LENOVO
* @description 针对表【recommend】的数据库操作Mapper
* @createDate 2025-11-12 15:45:39
* @Entity com.lyb.olinemusicserver.model.domain.Recommend
*/
public interface RecommendMapper extends BaseMapper<Recommend> {

    /**
     * @Description: 根据用户ID删除推荐数据
     * @param: userId 用户ID
     **/
    void deleteByUserId(Integer userId);

    /**
     * @Description: 批量插入推荐数据
     * @param: userId 用户ID
     * @param: songIds 歌曲ID数组
     **/
    void batchInsert(Integer userId, Integer[] songIds);
}




