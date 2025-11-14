package com.lyb.olinemusicserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.domain.Comment;
import com.lyb.olinemusicserver.model.request.CommentRequest;
import com.lyb.olinemusicserver.service.CommentService;
import com.lyb.olinemusicserver.mapper.CommentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author LENOVO
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2025-09-18 15:18:37
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Autowired
    private CommentMapper commentMapper;

    /**
     * @Description: 根据歌单id获取评论列表
     * @param: songListId 歌单id
     * @return: com.example.online.music.common.R
     **/
    @Override
    public R getCommentListBySongListId(Integer songListId) {
        return R.success("查询成功", commentMapper.selectCommentListBySongListId(songListId));
    }

    @Override
    public R getCommentListBySongId(Integer songId) {
        return R.success("查询成功", commentMapper.selectCommentListBySongId(songId));
    }

    @Override
    public R deleteComment(Integer id) {
        if (commentMapper.deleteById(id) > 0) {
            return R.success("删除成功");
        } else {
            return R.error("删除失败");
        }
    }

    @Override
    public R addComment(CommentRequest addCommentRequest) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(addCommentRequest, comment);
        comment.setType(addCommentRequest.getNowType());
        if (commentMapper.insert(comment) > 0) {
            return R.success("评论成功");
        } else {
            return R.error("评论失败");
        }
    }


}




