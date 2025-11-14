package com.lyb.olinemusicserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.domain.RankList;
import com.lyb.olinemusicserver.model.request.RankListRequest;
import com.lyb.olinemusicserver.service.RankListService;
import com.lyb.olinemusicserver.mapper.RankListMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author LENOVO
* @description 针对表【rank_list】的数据库操作Service实现
* @createDate 2025-09-25 15:59:49
*/
@Service
public class RankListServiceImpl extends ServiceImpl<RankListMapper, RankList>
    implements RankListService{

    @Autowired
    private RankListMapper rankMapper;

    /**
     * @Description: 歌单评价
     * @CreateTime:
     * @param: rankListAddRequest 歌单评价请求模型类
     * @return: cn.com.niit.online.music.common.R
     */
    @Override
    public R addRank(RankListRequest rankListAddRequest) {
        RankList rankList = new RankList();
        BeanUtils.copyProperties(rankListAddRequest, rankList);
        if (rankMapper.insert(rankList) > 0) {
            return R.success("评价成功");
        } else {
            return R.error("评价失败");
        }
    }

    /**
     * @Description: 获取歌单评分
     * @CreateTime:
     * @param: songListId 歌单id
     * @return: cn.com.niit.online.music.common.R
     */
    @Override
    public R rankOfSongListId(Long songListId) {
        // 评分总人数如果为 0，则返回0；否则返回计算出的结果
        QueryWrapper<RankList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("song_list_id",songListId);
        /** 获取评分总人数 */
        Long rankNum = rankMapper.selectCount(queryWrapper);
        /** 如果评分总人数大于0，则查询歌单评分总和并处以总人数得出该歌单评分 */
        return R.success(null, (rankNum <= 0) ? 0 : rankMapper.selectScoreSum(songListId) / rankNum);
    }

//    获取指定用户的歌单评分
    @Override
    public R rankOfUserId(Long consumerId, Long songListId) {
        QueryWrapper<RankList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("consumer_id",consumerId)
                .eq("song_list_id",songListId);
        RankList rankList = rankMapper.selectOne(queryWrapper);
        if (rankList != null) {
            return R.success("查询评分成功", rankList.getScore());
        }else{
            return R.success("暂无评分",null);
        }
    }
}




