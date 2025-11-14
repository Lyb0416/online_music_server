package com.lyb.olinemusicserver;

import com.lyb.olinemusicserver.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class TestCommentService {
    @Autowired
    private CommentService commentService;

    @Test
    public void testGetCommentListBySongListId(){
        log.info("测试获取指定歌单所有评论列表：{}", commentService.getCommentListBySongListId(5));
    }
}
