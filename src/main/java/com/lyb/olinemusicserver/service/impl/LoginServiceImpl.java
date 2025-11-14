package com.lyb.olinemusicserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyb.olinemusicserver.common.AliSmsUtil;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.common.RandomCodeGenerate;
import com.lyb.olinemusicserver.common.RedisUtil;
import com.lyb.olinemusicserver.mapper.ConsumerMapper;
import com.lyb.olinemusicserver.model.domain.Consumer;
import com.lyb.olinemusicserver.model.request.ConsumerPhoneCodeRequest;
import com.lyb.olinemusicserver.service.ConsumerService;
import com.lyb.olinemusicserver.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * 短信验证业务实现类 --Alice
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final String SMS_KEY_PRIFIX="sms:code:phone:"; //定义验证码key前缀
    private static final String MAIL_KEY_PRIFIX="mail:code:uuid:"; //定义验证码key前缀

    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private ConsumerMapper consumerMapper;
    @Autowired
    private RedisUtil redis;

    /**
     * 发送短信验证码--Alice
     * @param phone
     * @return
     */
    @Override
    public R sendRCode(String phone) {
        //校验参数
        if(phone.matches("^1[3-9]\\d{9}$")){
            try {
                //生成验证码
                Integer checkCode = RandomCodeGenerate.createCode(6);
                //发送短信
                if(AliSmsUtil.sendMsgCode(phone,checkCode+"")!=-1){
                    //记录验证码 有效期5分钟 使用Redis存储 数据类型 String 存储：key: sms:code:phone:手机号 value：验证码
                    redis.set(SMS_KEY_PRIFIX+phone,checkCode.toString(),300);
                    //返回结果
                    return R.success("验证码发送成功");
                }
                return  R.error("服务器异常，短信发送失败，请稍后重新尝试");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return R.error("服务器异常，请联系管理员");
        }
        return R.error("手机号不合法，请填写正确的手机号");
    }

    /**
     * 验证短信验证码--Alice
     * @param phoneCode
     * @return
     */
    @Override
    public R checkRCode(ConsumerPhoneCodeRequest phoneCode) {
        if(null!=phoneCode){
            if(!StringUtils.isBlank(phoneCode.getPhoneNum()) && !StringUtils.isBlank(phoneCode.getCheckCode())){
                //校验是否存在验证码
                String codeInRedis = (String)redis.get(SMS_KEY_PRIFIX+phoneCode.getPhoneNum());
                if (StringUtils.isBlank(codeInRedis)){
                    return R.error("验证码已失效，请重新发送");
                }else if(codeInRedis.equals(phoneCode.getCheckCode())){//验证码是否正确
                    Consumer consumer = new Consumer();
                    BeanUtils.copyProperties(phoneCode, consumer);
                    if(!consumerService.existUserByPhone(phoneCode.getPhoneNum())){
                        consumer.setUsername(phoneCode.getPhoneNum());
                        consumer.setAvator("img/avatorImages/user.jpg");
                        consumer.setPassword("123456");
                        //如果不存在，创建用户对象并保存到数据库
                        if(consumerMapper.insert(consumer) < 0){
                            return R.error("用户注册失败，请重试");
                        }
                    }
                    //成功后删除验证码
                    redis.del(SMS_KEY_PRIFIX+phoneCode.getPhoneNum());
                    return R.success("注册成功，并登录",consumerMapper.selectList(new QueryWrapper<>(consumer)));
                }else{
                    return R.error("验证码错误");
                }
            }
            return R.error("不能提交空数据");
        }
        return R.fatal("服务器异常，请联系管理员");
    }
}
