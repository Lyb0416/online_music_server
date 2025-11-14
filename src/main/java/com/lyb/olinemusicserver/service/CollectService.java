package com.lyb.olinemusicserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.domain.Collect;

import java.util.List;

public interface CollectService extends IService<Collect> {

    /**
     * @Description: 获取用户收藏的所有歌曲
     * @param userId: 用户ID
     * @return: com.lyb.olinemusicserver.common.R
     **/
    R collectionOfUser(Integer userId);

    /** 获取所有用户收藏记录 */
    List<Collect> getAllRecords();
}
