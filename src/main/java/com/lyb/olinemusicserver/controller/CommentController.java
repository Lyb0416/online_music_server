package com.lyb.olinemusicserver.controller;

import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.request.CommentRequest;
import com.lyb.olinemusicserver.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    //根据歌单id获取评论列表
    @GetMapping("/comment/songList/{songListId}")
    public R commentListOfSongListId(@PathVariable("songListId") Integer songListId) {
        return commentService.getCommentListBySongListId(songListId);
    }

    //歌曲的评论
    @GetMapping("/comment/song/{songId}")
    public R commentListOfSongId(@PathVariable("songId") Integer songId) {
        return commentService.getCommentListBySongId(songId);
    }

    // 删除评论
    @GetMapping("comment/delete")
    public R deleteComment(@RequestParam int id) {
        return commentService.deleteComment(id);
    }

    // 提交评论
    @PostMapping("/comment/add")
    public R addComment(@RequestBody CommentRequest addCommentRequest) {
        return commentService.addComment(addCommentRequest);
    }

}
