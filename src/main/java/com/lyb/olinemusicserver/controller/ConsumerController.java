package com.lyb.olinemusicserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyb.olinemusicserver.common.JwtUtils;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.common.Token;
import com.lyb.olinemusicserver.model.domain.Consumer;
import com.lyb.olinemusicserver.model.request.ConsumerRequest;
import com.lyb.olinemusicserver.service.ConsumerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    /**
     *管理界面调用：返回所有用户
     */
    @GetMapping("/user")
    public R allUser() {
        return consumerService.allUser();
    }

    @GetMapping("/users")
    public R allUsers(@RequestParam(required = false)Integer currentPage,
                      @RequestParam(required = false)Integer pageSize) {
        if (currentPage == null || pageSize == null) {
            // 没传分页参数 → 返回所有用户
            return consumerService.allUsers();
        }
        // 传了分页参数 → 返回分页用户
        return consumerService.allUser(currentPage, pageSize);
    }

    /**
     *删除用户
     */
    @GetMapping("/user/delete")
    public R deleteUser(@RequestParam int id) {
        return consumerService.deleteUser(id);
    }

    @DeleteMapping("user/deleteIds")
    public R deleteUsers(@RequestParam String[] id){
        return consumerService.deleteUsers(id);
    }

    @GetMapping("/user/detail")
    public R userOfId(@RequestParam int id) {
        return consumerService.userOfId(id);
    }

    /**
     * 前台页面调用  登录
     * 登录判断
     */
    @PostMapping("/user/login/status")
    public R loginStatus(@RequestBody ConsumerRequest loginRequest, HttpSession session) {
        R result = consumerService.loginStatus(loginRequest, session);
        Map<String,Object> map = new HashMap<>();
        map.put("res",result.getData());
        //登录成功的话，返回中需要包含token信息
        if(result.getSuccess()){
            String tokenStr = JwtUtils.generateToken(loginRequest.getUsername());
            Token token = new Token();
            token.setAuthorization(tokenStr);
            map.put("token",token);
        }
        result.setData(map);
        return result;
    }

    /**
     * 用户注册
     */
    @PostMapping("/user/add")
    public R addUser(@RequestBody ConsumerRequest registryRequest) {
        return consumerService.addUser(registryRequest);
    }

    /**
     * 前后台界面的调用
     * 更新用户信息
     */
    @PostMapping("/user/update")
    public R updateUserMsg(@RequestBody ConsumerRequest updateRequest) {
        return consumerService.updateUserMsg(updateRequest);
    }

    /**
     * 更新用户头像
     */
    @PostMapping("/user/avatar/update")
    public R updateUserPic(@RequestParam("file") MultipartFile avatorFile,
                           @RequestParam("id") int id) {
        return consumerService.updateUserAvator(avatorFile, id);
    }

    /**
     * 后台更新用户的密码
     * 更新用户密码
     */
    @PostMapping("/user/updatePassword")
    public R updatePassword(@RequestBody ConsumerRequest updatePasswordRequest) {
        return consumerService.updatePassword(updatePasswordRequest);
    }


}
