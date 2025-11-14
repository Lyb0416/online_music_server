package com.lyb.olinemusicserver.config;

import com.lyb.olinemusicserver.constant.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SpringBoot解决跨域方案有多种，重写WebMvcConfigurer是全局解决跨域方案。
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    public WebMvcConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4000","http://localhost:5173")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS","HEAD","PATCH")
                .allowedHeaders("Authorization","Content-Type","X-Requested-With")
                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .maxAge(3600);
    }
    //这个配置类的目的是什么  就是注册了一个类似于拦截器把  看到对应的资源 会将其修改成相应的地址
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/avatorImages/**")
                .addResourceLocations(Constants.AVATOR_IMAGES_PATH);
        registry.addResourceHandler("/img/singerPic/**")
                .addResourceLocations(Constants.SINGER_PIC_PATH);
        registry.addResourceHandler("/img/songPic/**")
                .addResourceLocations(Constants.SONG_PIC_PATH);
        registry.addResourceHandler("/song/**")
                .addResourceLocations(Constants.SONG_PATH);
        registry.addResourceHandler("/img/songListPic/**")
                .addResourceLocations(Constants.SONGLIST_PIC_PATH);
        registry.addResourceHandler("/img/swiper/**")
                .addResourceLocations(Constants.BANNER_PIC_PATH);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册JWT拦截器，拦截所有请求，但排除登录相关接口
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns("/admin/login/status")  // 排除管理员登录接口
                .excludePathPatterns("/user/login/status")  // 排除用户登录接口
                .excludePathPatterns("/img/**")     // 排除图片资源
                .excludePathPatterns("/song/**")    // 排除歌曲资源
                .excludePathPatterns("/user/add");  // 排除用户注册接口
    }
}
