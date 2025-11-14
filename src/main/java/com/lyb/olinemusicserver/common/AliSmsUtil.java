package com.lyb.olinemusicserver.common;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;

/**
 * 阿里云短信服务-处理器类
 * by Alice.Li
 */
public class AliSmsUtil {
    public static Client client;
    //使用AK&SK初始化账号Client
    static {
        Config config = new Config()
                // 请确保设置了环境变量ALIBABA_CLOUD_ACCESS_KEY_ID和ALIBABA_CLOUD_ACCESS_KEY_SECRET.
                // 必填，您的 AccessKey ID LTAI5tPPrzhEzJZiL7DT9FRA
                //.setAccessKeyId(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"))
                .setAccessKeyId("LTAI5tGnbeYGjPiAmYUHGHZi")
                // 必填，您的 AccessKey Secret 5Kt89B3BwxdnLCi98z78YEZWbXXhJ2
                //.setAccessKeySecret(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));
                .setAccessKeySecret("eCkoFvmcsZjK4iMv060LYniwFITmtP");
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        try {
            client=new Client(config);
        } catch (Exception e) {
            Common.assertAsString(e.getMessage());
        }
    }

    /**
     * 调用Api发送短信
     * @param phone 待接收的手机号码
     * @param code 待发送的验证码
     */
    public static int sendMsgCode(String phone,String code) {
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例使用环境变量获取 AccessKey 的方式进行调用，仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("阿里云短信测试") //签名
                .setTemplateCode("SMS_154950909")//模版CODE
                .setPhoneNumbers(phone)
                .setTemplateParam("{\"code\":\""+code+"\"}");
        RuntimeOptions runtime = new RuntimeOptions();
        SendSmsResponse resp = null;
        try {
            resp = client.sendSmsWithOptions(sendSmsRequest, runtime);
            /* 返回的结果
                {
                "headers":{"access-control-allow-origin":"*","date":"Tue, 20 Jun 2023 14:36:07 GMT","content-length":"110","keep-alive":"timeout=25","x-acs-request-id":"A5325F7F-CEBD-5316-AD40-D5B5D35C4D9D","connection":"keep-alive","content-type":"application/json;charset=utf-8","access-control-expose-headers":"*","x-acs-trace-id":"dfd87562a8a7e89576eeb1197064eef3"},
                "statusCode":200,
                "body":{"bizId":"228919487271767279^0","code":"OK","message":"OK","requestId":"A5325F7F-CEBD-5316-AD40-D5B5D35C4D9D"}
                }
             */
            return resp.getStatusCode();
        }catch (Exception e){
            e.printStackTrace();
            Common.assertAsString(e.getMessage());
        }
        return -1;//表示短信发送失败
    }
}
