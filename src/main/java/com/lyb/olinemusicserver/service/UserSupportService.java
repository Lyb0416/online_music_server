package com.lyb.olinemusicserver.service;

import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.domain.UserSupport;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyb.olinemusicserver.model.request.UserSupportRequest;

/**
* @author LENOVO
* @description 针对表【user_support】的数据库操作Service
* @createDate 2025-10-11 08:20:06
*/
public interface UserSupportService extends IService<UserSupport> {

    R toSupport(UserSupportRequest userSupportRequest);

    R checkSupport(Integer userId, Integer commentId);
}
