package com.lyb.olinemusicserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.olinemusicserver.model.domain.Banner;
import com.lyb.olinemusicserver.service.BannerService;
import com.lyb.olinemusicserver.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【banner】的数据库操作Service实现
* @createDate 2025-09-23 15:06:27
*/
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner>
    implements BannerService{

    @Autowired
    private BannerMapper bannerMapper;

    /**
     * @Cacheable 放在缓存中 redis 是以key-value进行存储的
     */
    @Cacheable(value = "banner", key = "'list'")
    @Override
    public List<Banner> getAllBanner() {
        return bannerMapper.selectList(null);
    }

}




