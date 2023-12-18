package com.ecommerce.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.ecommerce.constant.enums.ResultEnum;
import com.ecommerce.model.vo.Result;
import com.ecommerce.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override// 目标资源方法运行前运行，返回true:放行，返回false，不放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取请求的url
        String url = request.getRequestURL().toString();
        log.info("请求的url：{}",url);

        // 2.获取请求头中的令牌（token）
        String jwt = request.getHeader("token");

        // 3.判断令牌是否存在。如果不存在，返回错误结果（未登录）。
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头token为空，返回为登录的信息");
            Result error = Result.error(ResultEnum.NOT_LOGIN);
            // 手动转换 对象--json ------> 阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }

        // 4.解析token,如果解析失败，返回结果（未登录）。
        try{
            Claims claims = JwtUtils.parseJWT(jwt);
            String phoneNumber = claims.get("phoneNumber", String.class);
            String type = claims.get("type", String.class);
            // 将解析出来的信息存入请求域中
            request.setAttribute("phoneNumber",phoneNumber);
            request.setAttribute("type",type);
        }catch(Exception e){
            log.info("解析令牌失败，返回未登录错误信息");
            Result error = Result.error(ResultEnum.NOT_LOGIN);
            // 手动转换 对象--json ------> 阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }

        // 5.放行
        log.info("令牌合法，放行");
        return true;
    }

    @Override // 目标资源方法运行后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override // 视图渲染完毕后运行，最后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
