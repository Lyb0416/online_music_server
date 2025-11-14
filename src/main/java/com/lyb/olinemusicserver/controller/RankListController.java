package com.lyb.olinemusicserver.controller;

import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.request.RankListRequest;
import com.lyb.olinemusicserver.service.RankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RankListController {

    @Autowired
    private RankListService rankListService;

    // 提交评分
    @PostMapping("/rankList/add")
    public R addRank(@RequestBody RankListRequest rankListAddRequest) {
        return rankListService.addRank(rankListAddRequest);
    }

    // 取指定歌单的评分
    @GetMapping("/rankList")
    public R rankOfSongListId(@RequestParam Long songListId) {
        return rankListService.rankOfSongListId(songListId);
    }

    //获取指定用户的歌单评分
    @GetMapping("/rankList/user")
    public R rankOfUserId(@RequestParam Long consumerId,
                          @RequestParam Long songListId) {
        return rankListService.rankOfUserId(consumerId,songListId);
    }

}
