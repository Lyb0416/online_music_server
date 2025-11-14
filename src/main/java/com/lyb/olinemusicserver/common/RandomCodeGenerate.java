package com.lyb.olinemusicserver.common;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomCodeGenerate {
    /**
     * 随机生成指定长度的数字
     * @param length 制定的数字长度
     */
    public static Integer createCode(int length) throws NoSuchAlgorithmException {
        StringBuffer buffer = new StringBuffer();
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        int l = (int)Math.pow(10,length-1);
        //4 1000-9999 1000+随机（0-9000）
        //6 100000-999999 100000+随机（0-900000）
        return l+random.nextInt(9*l);
    }
}
