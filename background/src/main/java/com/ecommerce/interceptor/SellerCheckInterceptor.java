package com.ecommerce.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.ecommerce.constant.enums.ResultEnum;
import com.ecommerce.model.vo.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@Component
public class SellerCheckInterceptor implements HandlerInterceptor {
    @Override// 目标资源方法运行前运行，返回true:放行，返回false，不放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String type = request.getAttribute("type").toString();

        if(!type.equals("1")){
            log.info("用户类型不是卖家，返回错误信息");
            Result error = Result.error(ResultEnum.NOT_SELLER);
            // 手动转换 对象--json ------> 阿里巴巴fastJSON
            String notSeller = JSONObject.toJSONString(error);
            response.getWriter().write(notSeller);
            return false;
        }
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
