package com.lyb.olinemusicserver;

import com.lyb.olinemusicserver.model.domain.Banner;
import com.lyb.olinemusicserver.service.BannerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestBannerService {
    @Autowired
    private BannerService bannerService;

    @Test
    public void bannerServiceTest(){
        List<Banner> allBanner = bannerService.getAllBanner();
        Assert.assertNotNull("测试数据正确",allBanner);
    }
}
