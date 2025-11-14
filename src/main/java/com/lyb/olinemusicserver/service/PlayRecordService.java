package com.lyb.olinemusicserver.service;

import com.lyb.olinemusicserver.model.domain.PlayRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【play_record】的数据库操作Service
* @createDate 2025-11-10 11:43:15
*/
public interface PlayRecordService extends IService<PlayRecord> {

    /** 获取所有用户播放记录 */
    List<PlayRecord> getAllRecords();
}
