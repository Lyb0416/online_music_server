package com.lyb.olinemusicserver;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyb.olinemusicserver.mapper.AdminMapper;
import com.lyb.olinemusicserver.model.domain.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OlineMusicServerApplicationTests {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 测试登录使用的方法
     */
    @Test
    public void testSelectCount(){
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","admin");
        queryWrapper.eq("password","123");
        long num = adminMapper.selectCount(queryWrapper);
    }

}
