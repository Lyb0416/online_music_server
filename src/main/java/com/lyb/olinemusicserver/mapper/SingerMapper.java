package com.lyb.olinemusicserver.mapper;

import com.lyb.olinemusicserver.model.domain.Singer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

/**
* @author LENOVO
* @description 针对表【singer】的数据库操作Mapper
* @createDate 2025-09-16 15:06:04
* @Entity com.lyb.olinemusicserver.model.domain.Singer
*/
public interface SingerMapper extends BaseMapper<Singer> {
    int deleteIds(@Param("ids")String string);
}




