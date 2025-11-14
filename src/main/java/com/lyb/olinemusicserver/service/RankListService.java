package com.lyb.olinemusicserver.service;

import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.domain.RankList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyb.olinemusicserver.model.request.RankListRequest;

/**
* @author LENOVO
* @description 针对表【rank_list】的数据库操作Service
* @createDate 2025-09-25 15:59:49
*/
public interface RankListService extends IService<RankList> {

    R addRank(RankListRequest rankListAddRequest);

    R rankOfSongListId(Long songListId);

    R rankOfUserId(Long consumerId, Long songListId);
}
