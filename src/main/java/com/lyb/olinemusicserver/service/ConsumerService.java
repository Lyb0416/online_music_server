package com.lyb.olinemusicserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.domain.Consumer;
import com.lyb.olinemusicserver.model.request.ConsumerRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ConsumerService extends IService<Consumer> {
    R allUser();

    R allUsers();

    R allUser(Integer currentPage, Integer pageSize);

    R deleteUser(Integer id);

    R deleteUsers(String[] id);

    R userOfId(int id);

    R loginStatus(ConsumerRequest loginRequest, HttpSession session);

    boolean verityPasswd(String username, String password);

    R addUser(ConsumerRequest registryRequest);

    R updateUserMsg(ConsumerRequest updateRequest);

    R updateUserAvator(MultipartFile avatorFile, int id);

    R updatePassword(ConsumerRequest updatePasswordRequest);

    /**
     * 验证注册或登录时 手机号是否存在
     * @param phoneNum
     */
    boolean existUserByPhone(String phoneNum);

    /** 获取所有用户ID */
    List<Integer> getAllUserIds();
}
