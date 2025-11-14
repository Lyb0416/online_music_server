package com.lyb.olinemusicserver.service;

import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.domain.Song;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyb.olinemusicserver.model.request.SongRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【song】的数据库操作Service
* @createDate 2025-09-16 16:35:45
*/
public interface SongService extends IService<Song> {

    R songOfSingerId(int singerId);

    R songOfSongListId(int songListId);

    R addSong(SongRequest addSongRequest, MultipartFile mpfile);

    R songOfSongName(String name);

    R allSong();

    R urlOfSong(Integer id);

    /** 获取所有歌曲ID */
    List<Integer> getAllSongIds();

    /**
     * @Description: 根据用户id获取该用户的推荐歌曲列表
     * @CreateTime:
     * @param: userId
     * @return: com.example.online.music.common.R
     **/
    R getRecommendSongList(Integer userId);
}
