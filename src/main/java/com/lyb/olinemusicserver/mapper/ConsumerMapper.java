package com.lyb.olinemusicserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyb.olinemusicserver.model.domain.Consumer;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumerMapper extends BaseMapper<Consumer> {

    int deleteByIds(@Param("ids")String ids);

    /** 查询所有用户ID 集合 */
    List<Integer> selectAllUserIds();
}
