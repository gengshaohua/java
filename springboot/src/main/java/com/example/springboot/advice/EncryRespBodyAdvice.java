package com.example.springboot.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@ControllerAdvice
public class EncryRespBodyAdvice implements ResponseBodyAdvice<Object> {
    @Resource
    private HttpServletRequest request;

    private boolean tokenEncrypt;

    private String getUserInfo;

    public EncryRespBodyAdvice() {
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        return 1;
    }

    private String encrypt(String data) {
        return null;
    }
}
