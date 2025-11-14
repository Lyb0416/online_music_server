package com.lyb.olinemusicserver.service;

import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.domain.SongList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyb.olinemusicserver.model.request.SongListRequest;

/**
* @author LENOVO
* @description 针对表【song_list】的数据库操作Service
* @createDate 2025-09-18 08:19:56
*/
public interface SongListService extends IService<SongList> {

    R songList(String style);

    R addSongList(SongListRequest addSongListRequest);

    R updateSongListMsg(SongListRequest updateSongListRequest);

    R deleteSongList(int id);

    R deleteSongLists(String[] ids);

    R likeTitle(String s);
}
