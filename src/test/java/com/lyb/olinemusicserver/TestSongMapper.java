package com.lyb.olinemusicserver;

import com.lyb.olinemusicserver.mapper.SongMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestSongMapper {

    @Autowired
    private SongMapper songMapper;

    @Test
    public void testSelectCollectSongByUserId(){
        System.out.println(songMapper.selectCollectSongByUserId(1));
    }
}
