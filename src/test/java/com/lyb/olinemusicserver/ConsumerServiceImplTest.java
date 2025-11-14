package com.lyb.olinemusicserver;

import com.lyb.olinemusicserver.common.JwtUtils;
import com.lyb.olinemusicserver.common.R;
import com.lyb.olinemusicserver.common.Token;
import com.lyb.olinemusicserver.constant.Constants;
import com.lyb.olinemusicserver.mapper.ConsumerMapper;
import com.lyb.olinemusicserver.model.domain.Consumer;
import com.lyb.olinemusicserver.model.request.ConsumerRequest;
import com.lyb.olinemusicserver.service.ConsumerService;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@SpringBootTest
class ConsumerServiceImplTest {

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    HttpSession session;
    @Qualifier("consumerMapper")
    @Autowired
    private ConsumerMapper consumerMapper;

    @Test
    void getAllRecords() {
        ConsumerRequest loginRequest = new ConsumerRequest();
        loginRequest.setUsername("Yin33");
        loginRequest.setPassword("123");

        R result = consumerService.loginStatus(loginRequest, session);

        if (result.getSuccess()) {
            String tokenStr = JwtUtils.generateToken(loginRequest.getUsername());
            Token<Object> token = new Token<>();
            token.setAuthorization(tokenStr);
            token.setObj(result.getData());
            result.setData(token);
        }

        System.out.println(result.getSuccess());
    }

    /**
     * 用户注册单元测试
     */
    @Test
    void consumerServiceAddUserTest1() {
        //组建要注册的用户
        Consumer consumer = new Consumer();
        consumer.setUsername("test01");
        consumer.setGender(1);
        consumer.setPhoneNum("17765434563");
        consumer.setIntroduction("爱好和平");
        consumer.setEmail("rrruuu@qq.com");
        consumer.setBirth(new Date());
        consumer.setPassword("123456");
        consumer.setLocation("北京");
        //md5加密
        String password = DigestUtils.md5DigestAsHex((Constants.SALT + consumer.getPassword()).getBytes(StandardCharsets.UTF_8));
        consumer.setPassword(password);

        if(StringUtils.isBlank(consumer.getPhoneNum())){
            consumer.setPhoneNum(null);
        }
        if("".equals(consumer.getEmail())){
            consumer.setEmail(null);
        }
        consumer.setAvator("img/avatorImages/user.jpg");
        if(consumerMapper.insert(consumer) > 0) {
            System.out.println("注册成功");
        }else{
            System.out.println("注册失败");
        }
    }

}
