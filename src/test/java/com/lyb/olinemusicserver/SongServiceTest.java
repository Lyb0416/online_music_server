package com.lyb.olinemusicserver;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyb.olinemusicserver.mapper.SongMapper;
import com.lyb.olinemusicserver.model.domain.Song;
import com.lyb.olinemusicserver.model.request.SongRequest;
import com.lyb.olinemusicserver.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

@Slf4j
@SpringBootTest
public class SongServiceTest {

    @Autowired
    private SongService songService;

    @Autowired
    private SongMapper songMapper;

    @Test
    public void testAddSong(){
        SongRequest songRequest = new SongRequest();
        songRequest.setName("ceshi");
        songRequest.setSingerId(1);
        //模拟 文件上传中的文件
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "filename.txt",
                "text/plain",
                "File content".getBytes()
        );

        log.info("测试获取全部歌手列表：{}", songService.addSong(songRequest,file));

    }

    @Test
    public void songList(){
        System.out.println(songService.songOfSongListId(1));
    }
    @Test
    public void songMapperTest(){
        QueryWrapper<Song> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","爱");
        List<Song> songs = songMapper.selectList(queryWrapper);
        for (Song s : songs) {
            System.out.println(s.toString());
        }
        Assert.assertThat(songs, Matchers.notNullValue());//assertThat断言后面介绍
    }

}
