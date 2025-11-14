package com.lyb.olinemusicserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.mapper.CollectMapper;
import com.lyb.olinemusicserver.mapper.SongMapper;
import com.lyb.olinemusicserver.model.domain.Collect;
import com.lyb.olinemusicserver.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private SongMapper songMapper;

    @Override
    public R collectionOfUser(Integer userId) {
        return R.success("用户收藏", songMapper.selectCollectSongByUserId(userId));
    }

    /**
     * @Description: 获取所有用户收藏记录
     * @return: java.util.List<com.example.online.music.model.domain.Collect>
     **/
    @Override
    public List<Collect> getAllRecords() {
        return collectMapper.selectAllRecords();
    }
}
