package com.lyb.olinemusicserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.olinemusicserver.model.domain.PlayRecord;
import com.lyb.olinemusicserver.service.PlayRecordService;
import com.lyb.olinemusicserver.mapper.PlayRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【play_record】的数据库操作Service实现
* @createDate 2025-11-10 11:43:15
*/
@Service
public class PlayRecordServiceImpl extends ServiceImpl<PlayRecordMapper, PlayRecord>
    implements PlayRecordService{

    @Autowired
    private PlayRecordMapper playRecordMapper;

    /**
     * @Description: 获取所有用户播放记录
     * @return: java.util.List<com.example.online.music.model.domain.PlayRecord>
     **/
    @Override
    public List<PlayRecord> getAllRecords() {
        return playRecordMapper.selectAllRecords();
    }
}




