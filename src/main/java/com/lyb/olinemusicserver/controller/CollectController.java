package com.lyb.olinemusicserver.controller;

import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class CollectController {
    @Autowired
    private CollectService collectService;

    /**
     * 返回的指定用户 ID 收藏的列表
     */
    @GetMapping("/collection/detail")
    public R collectionOfUser(@RequestParam Integer userId) {
        return collectService.collectionOfUser(userId);
    }
}
