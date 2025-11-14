package com.lyb.olinemusicserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.olinemusicserver.model.domain.DownloadRecord;
import com.lyb.olinemusicserver.service.DownloadRecordService;
import com.lyb.olinemusicserver.mapper.DownloadRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【download_record】的数据库操作Service实现
* @createDate 2025-11-10 11:44:24
*/
@Service
public class DownloadRecordServiceImpl extends ServiceImpl<DownloadRecordMapper, DownloadRecord>
    implements DownloadRecordService{

    @Autowired
    private DownloadRecordMapper downloadRecordMapper;

    /**
     * @Description: 获取所有用户下载记录
     * @return: java.util.List<com.example.online.music.model.domain.DownloadRecord>
     **/
    @Override
    public List<DownloadRecord> getAllRecords() {
        return downloadRecordMapper.selectAllRecords();
    }
}




