package com.lyb.olinemusicserver.controller;

import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.request.SongListRequest;
import com.lyb.olinemusicserver.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SongListController {

    @Autowired
    private SongListService songListService;

    /** 查询歌单列表，style 是歌单分类查询条件，该参数不传时查询所有分类歌单， */
    @GetMapping("/songList")
    public R songList(@RequestParam(required = false) String style) {
        return songListService.songList(style);
    }

    // 添加歌单
    @PostMapping("/songList/add")
    public R addSongList(@RequestBody SongListRequest addSongListRequest) {
        return songListService.addSongList(addSongListRequest);
    }

    // 编辑歌单
    @PostMapping("/songList/update")
    public R updateSongListMsg(@RequestBody SongListRequest updateSongListRequest) {
        return songListService.updateSongListMsg(updateSongListRequest);
    }

    // 删除歌单
    @DeleteMapping("/songList/delete")
    public R deleteSongList(@RequestParam int id) {
        return songListService.deleteSongList(id);
    }

    // 批量删除歌手
    @DeleteMapping("/songList/deleteIds")
    public R deleteSongList(@RequestParam String[] id) {
        return songListService.deleteSongLists(id);
    }

    // 返回标题包含文字的歌单
    @GetMapping("/songList/likeTitle/detail")
    public R songListOfLikeTitle(@RequestParam String title) {
        return songListService.likeTitle('%' + title + '%');
    }


}
