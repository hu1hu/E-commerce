package com.ecommerce.config;

import com.ecommerce.annotation.GetAttributeResolver;
import com.ecommerce.interceptor.BuyerCheckInterceptor;
import com.ecommerce.interceptor.LoginCheckInterceptor;
import com.ecommerce.interceptor.SellerCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration // 配置类
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;
    @Autowired
    private BuyerCheckInterceptor buyerCheckInterceptor;
    @Autowired
    private SellerCheckInterceptor sellerCheckInterceptor;

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/api/**") // 需要拦截所有请求
                .excludePathPatterns("/api/common/account/login") // 除了登录注册的请求
                .excludePathPatterns("/api/common/account/register");
        registry.addInterceptor(buyerCheckInterceptor)
                .addPathPatterns("/api/foreground/**") // 拦截前端的所有请求
                .excludePathPatterns("/api/foreground/product/detail") // 除了获取商品详情的请求
                ;
        registry.addInterceptor(sellerCheckInterceptor)
                .addPathPatterns("/api/background/**") // 拦截后端的所有请求
                ;
    }

    /**
     * 添加自定义参数解析器
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new GetAttributeResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有接口都支持跨域
                .allowedOrigins("*") // 允许的跨域请求来源
                .allowedMethods("GET","POST","PUT","DELETE") // 允许的HTTP方法
                .allowedHeaders("*"); // 允许的请求头信息
    }
}
