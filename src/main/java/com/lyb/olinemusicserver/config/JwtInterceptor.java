package com.lyb.olinemusicserver.config;

import com.alibaba.fastjson.JSON;
import com.lyb.olinemusicserver.common.JwtUtils;
import com.lyb.olinemusicserver.common.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 放行预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String uri = request.getRequestURI();
        // 2. 放行登录、注册接口
        if (uri.contains("/login") || uri.contains("/user/add")) {
            return true;
        }

        // 3. 获取 Authorization
        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (token == null || token.isEmpty()) {
            unauthorized(response, "缺少访问令牌");
            return false;
        }

        // 4. 验证 token
        try {
            if (!JwtUtils.verify(token)) {
                unauthorized(response, "无效的访问令牌");
                return false;
            }
        } catch (Exception e) {
            unauthorized(response, "令牌已过期或无效");
            return false;
        }

        return true;
    }

    private void unauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        R result = R.error(message);
        result.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(JSON.toJSONString(result));
    }
}

