package com.lyb.olinemusicserver.mapper;

import com.lyb.olinemusicserver.model.domain.PlayRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【play_record】的数据库操作Mapper
* @createDate 2025-11-10 11:43:15
* @Entity com.lyb.olinemusicserver.model.domain.PlayRecord
*/
public interface PlayRecordMapper extends BaseMapper<PlayRecord> {

    /** 查询所有播放记录 */
    List<PlayRecord> selectAllRecords();
}




