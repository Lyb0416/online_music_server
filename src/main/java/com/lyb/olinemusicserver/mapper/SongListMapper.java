package com.lyb.olinemusicserver.mapper;

import com.lyb.olinemusicserver.model.domain.SongList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

/**
* @author LENOVO
* @description 针对表【song_list】的数据库操作Mapper
* @createDate 2025-09-18 08:19:56
* @Entity com.lyb.olinemusicserver.model.domain.SongList
*/
public interface SongListMapper extends BaseMapper<SongList> {
    int deleteIds(@Param("ids")String string);
}




