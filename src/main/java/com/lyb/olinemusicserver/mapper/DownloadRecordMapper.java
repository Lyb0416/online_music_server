package com.lyb.olinemusicserver.mapper;

import com.lyb.olinemusicserver.model.domain.DownloadRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【download_record】的数据库操作Mapper
* @createDate 2025-11-10 11:44:24
* @Entity com.lyb.olinemusicserver.model.domain.DownloadRecord
*/
public interface DownloadRecordMapper extends BaseMapper<DownloadRecord> {

    /** 查询所有下载记录 */
    List<DownloadRecord> selectAllRecords();
}




