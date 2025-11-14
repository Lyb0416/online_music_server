package com.lyb.olinemusicserver.service;

import com.lyb.olinemusicserver.model.domain.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author LENOVO
* @description 针对表【banner】的数据库操作Service
* @createDate 2025-09-23 15:06:27
*/
public interface BannerService extends IService<Banner> {

    /**
     * 返回所有的轮播图信息
     * @return
     */
    List<Banner> getAllBanner();
}
