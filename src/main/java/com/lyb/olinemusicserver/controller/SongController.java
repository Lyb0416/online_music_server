package com.lyb.olinemusicserver.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.config.OssConfig;
import com.lyb.olinemusicserver.mapper.SongMapper;
import com.lyb.olinemusicserver.model.domain.Song;
import com.lyb.olinemusicserver.model.request.SongRequest;
import com.lyb.olinemusicserver.service.SongService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private OSS ossClient;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.endPoint}")
    private String endpoint;

    //获取歌手的歌曲
    @GetMapping("/song/singer/detail")
    public R songOfSingerId(@RequestParam int singerId) {
        return songService.songOfSingerId(singerId);
    }

    //获取歌单的歌曲
    @GetMapping("/song/songList/{songListId}")
    public R songOfSongListId(@PathVariable int songListId) {
        return songService.songOfSongListId(songListId);
    }

    // 添加歌曲
    @PostMapping("/song/add")
    public R addSong(SongRequest addSongRequest,
                     @RequestParam("file") MultipartFile mpfile) {
        return songService.addSong(addSongRequest, mpfile);
    }

    // 返回指定歌手名的歌曲
    @GetMapping("/song/songName/detail")
    public R songOfSingerName(@RequestParam String name) {
        return songService.songOfSongName(name);
    }

    // 返回所有歌曲
    @GetMapping("/song")
    public R allSong() {
        return songService.allSong();
    }

    // 返回歌曲的URL
    @GetMapping("/song/download")
    public R urlOfSong(@RequestParam Integer id) {
        return songService.urlOfSong(id);
    }

    /**
     * @Description: 推荐歌曲列表
     * @CreateTime:
     * @return: com.example.online.music.common.R
     **/
    @GetMapping({"/song/recommend", "/song/recommend/{userId}"})
    public R recommend(@PathVariable(value = "userId", required = false) Integer userId) {
        return songService.getRecommendSongList(userId);
    }
}
