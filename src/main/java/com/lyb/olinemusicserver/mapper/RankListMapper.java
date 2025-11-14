package com.lyb.olinemusicserver.mapper;

import com.lyb.olinemusicserver.model.domain.RankList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author LENOVO
* @description 针对表【rank_list】的数据库操作Mapper
* @createDate 2025-09-25 15:59:49
* @Entity com.lyb.olinemusicserver.model.domain.RankList
*/
public interface RankListMapper extends BaseMapper<RankList> {

    /**
     * 查总分
     * @param songListId
     * @return
     */
    int selectScoreSum(Long songListId);
}




