package com.lyb.olinemusicserver.service;

import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.model.request.ConsumerPhoneCodeRequest;

/**
 * 短信验证码相关业务接口
 */
public interface LoginService {

    /**
     * 手机短信发送验证码
     *
     * @param phone
     * @return
     */
    R sendRCode(String phone);

    /**
     * 手机短信验证码验证及登录
     *
     * @param phoneCode
     * @return
     */
    R checkRCode(ConsumerPhoneCodeRequest phoneCode);
}
