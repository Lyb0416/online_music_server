package com.lyb.olinemusicserver.controller;

import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.request.UserSupportRequest;
import com.lyb.olinemusicserver.service.UserSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userSupport")
public class UserSupportController {

    @Autowired
    private UserSupportService userSupportService;

    // 提交评论
    @PostMapping("/like")
    public R addComment(@RequestBody UserSupportRequest userSupportRequest) {
        return userSupportService.toSupport(userSupportRequest);
    }

    @GetMapping("/check")
    public R checkSupport(@RequestParam Integer userId, @RequestParam Integer commentId) {
        return userSupportService.checkSupport(userId, commentId);
    }

}
