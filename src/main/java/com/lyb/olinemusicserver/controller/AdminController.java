package com.lyb.olinemusicserver.controller;

import com.lyb.olinemusicserver.common.JwtUtils;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.common.Token;
import com.lyb.olinemusicserver.model.request.AdminRequest;
import com.lyb.olinemusicserver.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    // 判断是否登录成功
    @PostMapping("/admin/login/status")
    public R loginStatus(@RequestBody AdminRequest adminRequest, HttpSession session) {
        R result = adminService.verityPasswd(adminRequest, session);
        //登录成功的话，返回中包含token信息
        if(result.getSuccess()){
            String tokenStr = JwtUtils.generateToken(adminRequest.getUsername());
            Token token = new Token();
            token.setAuthorization(tokenStr);
            result.setData(token);
        }
        return result;
    }
}
