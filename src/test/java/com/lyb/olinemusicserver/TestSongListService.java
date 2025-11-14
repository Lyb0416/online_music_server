package com.lyb.olinemusicserver;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyb.olinemusicserver.mapper.SongListMapper;
import com.lyb.olinemusicserver.model.domain.SongList;
import com.lyb.olinemusicserver.model.request.SongListRequest;
import com.lyb.olinemusicserver.service.SongListService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Description: 用于测试歌单业务层方法
 */
@Slf4j
@SpringBootTest
public class TestSongListService {

    @Autowired
    private SongListService songListService;

    @Autowired
    private SongListMapper songListMapper;

    @Test
    public void testSongList(){
        log.info("测试获取全部歌曲列表：{}", songListService.songList(""));
        log.info("测试获取华语歌曲列表：{}", songListService.songList("华语"));
    }

    @Test
    public void testAddSongList(){
        SongListRequest addSongListRequest = new SongListRequest();
        addSongListRequest.setTitle("我喜欢的");
        addSongListRequest.setIntroduction("包含所有已经听过且比较喜欢的歌曲");
        addSongListRequest.setIntroduction("华语");
        log.info("测试新增歌单：{}", songListService.addSongList(addSongListRequest));
    }

    @Test
    public void testUpdateSongList(){
        SongListRequest updateSongListRequest = new SongListRequest();
        updateSongListRequest.setId(89);
        updateSongListRequest.setTitle("我爱听的");
        updateSongListRequest.setIntroduction("包含所有已经听过且比较喜欢的歌曲");
        updateSongListRequest.setIntroduction("华语");
        log.info("测试编辑歌单：{}", songListService.updateSongListMsg(updateSongListRequest));
    }

    @Test
    public void testDeleteSongList(){
        log.info("测试删除歌单：{}", songListService.deleteSongList(89));
    }

    @Test
    public void songListMapperTest(){
        QueryWrapper<SongList> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title","The");
        List<SongList> songLists = songListMapper.selectList(queryWrapper);
        for (SongList s : songLists) {
            System.out.println(s.toString());
        }
        Assert.assertThat(songLists, Matchers.notNullValue());//assertThat断言后面介绍
    }


}
