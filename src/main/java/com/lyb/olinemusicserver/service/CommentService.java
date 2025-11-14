package com.lyb.olinemusicserver.service;

import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.domain.Comment;
import com.lyb.olinemusicserver.model.request.CommentRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author LENOVO
* @description 针对表【comment】的数据库操作Service
* @createDate 2025-09-18 15:18:37
*/
public interface CommentService extends IService<Comment> {

    /**
     * @Description: 根据歌单id获取评论列表
     * @param: songListId 歌单id
     * @return: com.example.online.music.common.R
     **/
    R getCommentListBySongListId(Integer songListId);

    R getCommentListBySongId(Integer songId);

    R deleteComment(Integer id);

    R addComment(CommentRequest addCommentRequest);
}
