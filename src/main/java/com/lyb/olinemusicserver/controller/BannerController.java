package com.lyb.olinemusicserver.controller;

import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 轮播图接口
 */
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/getAllBanner")
    public R getAllBanner() {
        return R.success("成功获取轮播图与", bannerService.getAllBanner());
    }
}
