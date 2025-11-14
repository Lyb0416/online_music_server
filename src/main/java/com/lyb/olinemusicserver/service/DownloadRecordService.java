package com.lyb.olinemusicserver.service;

import com.lyb.olinemusicserver.model.domain.DownloadRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【download_record】的数据库操作Service
* @createDate 2025-11-10 11:44:24
*/
public interface DownloadRecordService extends IService<DownloadRecord> {

    /** 获取所有用户下载记录 */
    List<DownloadRecord> getAllRecords();
}
