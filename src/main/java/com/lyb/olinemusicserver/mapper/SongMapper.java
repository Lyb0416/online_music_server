package com.lyb.olinemusicserver.mapper;

import com.lyb.olinemusicserver.model.domain.Song;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【song】的数据库操作Mapper
* @createDate 2025-09-16 16:35:45
* @Entity com.lyb.olinemusicserver.model.domain.Song
*/
public interface SongMapper extends BaseMapper<Song> {

    List<Song> selectSongListByListId(int songListId);

    List<Song> selectCollectSongByUserId(@Param("userId")int songId);

    /** 查询所有歌曲ID 集合 */
    List<Integer> selectAllSongIds();

    /**
     * @Description: 查询该用户所有推荐歌曲列表
     * @CreateTime:
     * @param: userId
     * @return: java.util.List<com.example.online.music.model.domain.Song>
     **/
    List<Song> selectAllRecommendSongList(Integer userId);
}




