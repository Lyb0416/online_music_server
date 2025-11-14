package com.lyb.olinemusicserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyb.olinemusicserver.model.domain.Collect;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectMapper extends BaseMapper<Collect> {

    /** 查询所有收藏记录 */
    List<Collect> selectAllRecords();
}
