package com.lyb.olinemusicserver.mapper;

import com.lyb.olinemusicserver.model.domain.Comment;
import com.lyb.olinemusicserver.model.request.CommentRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2025-09-18 15:18:37
* @Entity com.lyb.olinemusicserver.model.domain.Comment
*/
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * @Description: 根据歌单id查询所有评论列表
     * @CreateTime:
     * @param: songListId
     * @return: java.util.List<com.example.online.music.model.response.CommentResponsePO>
     **/
    List<Comment> selectCommentListBySongListId(Integer songListId);

    List<Comment> selectCommentListBySongId(Integer songId);

    /**
     * @Description: 更新点赞数：用于用户点赞或取消点赞
     * @Author: yang_yong
     * @CreateTime:
     * @param: id 评论id
     * @param: up 1 点赞，-1 取消点赞
     **/
    void updateUpById(@Param("id") Integer id, @Param("up") int up);

}




