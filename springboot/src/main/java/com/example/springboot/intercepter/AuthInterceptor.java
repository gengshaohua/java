package com.example.springboot.intercepter;

import com.alibaba.fastjson.JSON;
import com.example.springboot.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        /*log.info("当前进入拦截器");
        setReturn(response, 401, "用户无权限访问");
        return false;*/
        log.info("进入拦截器判断");
        return true;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        return;
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        return;
    }

    //返回错误信息
    private static void setReturn(HttpServletResponse response, int code, String msg) throws IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSON.toJSONString(R.error(code, msg)));
    }
 
}