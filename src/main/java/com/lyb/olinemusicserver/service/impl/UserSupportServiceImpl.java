package com.lyb.olinemusicserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.mapper.CommentMapper;
import com.lyb.olinemusicserver.model.domain.Comment;
import com.lyb.olinemusicserver.model.domain.UserSupport;
import com.lyb.olinemusicserver.model.request.UserSupportRequest;
import com.lyb.olinemusicserver.service.UserSupportService;
import com.lyb.olinemusicserver.mapper.UserSupportMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author LENOVO
* @description 针对表【user_support】的数据库操作Service实现
* @createDate 2025-10-11 08:20:06
*/
@Service
public class UserSupportServiceImpl extends ServiceImpl<UserSupportMapper, UserSupport>
    implements UserSupportService{

    @Autowired
    private UserSupportMapper userSupportMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transactional //事务管理
    public R toSupport(UserSupportRequest userSupportRequest) {
        Comment comment = commentMapper.selectById(userSupportRequest.getCommentId());
        if(null != comment){
            int up = comment.getUp();
            QueryWrapper<UserSupport> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("comment_id", userSupportRequest.getCommentId());
            queryWrapper.eq("user_id", userSupportRequest.getUserId());
            UserSupport userSupport = userSupportMapper.selectOne(queryWrapper);
            if(null == userSupport){//不存在，则点赞
                userSupport = new UserSupport();
                BeanUtils.copyProperties(userSupportRequest, userSupport);
                userSupportMapper.insert(userSupport);
                commentMapper.updateUpById(comment.getId(), 1);
                return R.success("点赞成功", up + 1);
            }else{  //存在，则取消点赞
                userSupportMapper.delete(queryWrapper);
                commentMapper.updateUpById(comment.getId(), -1);
                return R.success("您已取消点赞", up - 1);
            }
        }else{
            return R.error("评论不存在或已被删除！");
        }
    }

    @Override
    public R checkSupport(Integer userId, Integer commentId) {
        QueryWrapper<UserSupport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("comment_id", commentId);
        UserSupport userSupport = userSupportMapper.selectOne(queryWrapper);
        if(null == userSupport){
            return R.success("该用户未点赞该评论", false);
        }else{
            return R.success("该用户已点赞该评论", true);
        }
    }
}




