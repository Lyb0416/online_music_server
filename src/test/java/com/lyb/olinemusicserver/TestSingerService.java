package com.lyb.olinemusicserver;

import com.lyb.olinemusicserver.model.request.SingerRequest;
import com.lyb.olinemusicserver.model.request.SongRequest;
import com.lyb.olinemusicserver.service.SingerService;
import com.lyb.olinemusicserver.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

@Slf4j
@SpringBootTest
public class TestSingerService {

    @Autowired
    private SingerService singerService;

    @Autowired
    private SongService songService;

    @Test
    public void testSingerList(){
        log.info("测试获取全部歌手列表：{}", singerService.allSinger(1,10));

    }

    @Test
    public void testAddSinger(){
        SingerRequest singerRequest = new SingerRequest();
        singerRequest.setPic("img/avatorImages/user.jpg");
        singerRequest.setId(48);
        singerRequest.setName("jefffffff");
        log.info("测试添加歌手：{}", singerService.addSinger(singerRequest));
    }

    @Test
    public void testDeleteSinger() {

        log.info("测试删除新的歌手：{}", singerService.deleteSinger(111116));

    }

    @Test
    public void testUpdateSinger() {
        SingerRequest singerRequest = new SingerRequest();
        singerRequest.setPic("xxxx");
        singerRequest.setId(47);
        singerRequest.setName("张三1");
        log.info("测试更新歌手信息：{}", singerService.updateSingerMsg(singerRequest));

    }

    @Test
    public void testSongSinger() {

        log.info("该歌手的歌曲：{}", songService.songOfSingerId(1));

    }
}
