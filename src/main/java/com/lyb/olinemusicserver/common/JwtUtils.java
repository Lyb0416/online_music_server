package com.lyb.olinemusicserver.common;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
JWT工具类
 */
public class JwtUtils {
    public static final String SECRET_KEY = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901";
    // 过期时间，单位为秒
    private static final long EXPIRATION_TIME = 7 * 24 * 60 * 60; // 7天

    //生成 JWT   subject 用户信息
    public static String generateToken(String subject) {
        // 计算过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000);
        return  Jwts.builder()
                .setSubject(subject)
                .setExpiration(expirationDate)
                // 使用 Keys.hmacShaKeyFor()：这是JWT库推荐的创建签名密钥的方式，它会自动根据密钥内容生成合适的密钥
                // 会根据密钥长度自动选择合适的算法（对于您的密钥长度，会自动选择HS512
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public static String generate(String name, String id) {
        Map<String, Object> subject = new HashMap<>();
        subject.put("name", name);
        subject.put("id", id);
        // 计算过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000);
        return Jwts.builder()
                .setClaims(subject)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    //解析 JWT
    public static Claims parseToken(String jwt) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    //验证JWT是否过期
    public static boolean isTokenExpired(String jwt) {
        try {
            Claims claims = parseToken(jwt);
            Date expirationDate = claims.getExpiration();
            return expirationDate.before(new Date());
        } catch (Exception e) {
            //过期会抛异常，所以直接捕捉  返回ture代表已过期
            throw new ExpiredJwtException(null, null, "Token已经过期");
            //return true;
        }
    }

    //校验合法行
    public static boolean verify(String token) {
        /**
         * 使用 Keys.hmacShaKeyFor()：这是JWT库推荐的创建签名密钥的方式，它会自动根据密钥内容生成合适的密钥
         * 指定字符集：使用 SECRET_KEY.getBytes(StandardCharsets.UTF_8) 确保字节转换的一致性
         * 移除显式算法指定：Keys.hmacShaKeyFor() 会根据密钥长度自动选择合适的算法（对于您的密钥长度，会自动选择HS512
         */
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .setAllowedClockSkewSeconds(10)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // 额外检查是否过期
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    //临时测试
    public static void main(String[] args) {
        String token = generateToken("admin");
        System.out.println(token);
        System.out.println(parseToken(token));
        System.out.println(isTokenExpired(token));
    }
}