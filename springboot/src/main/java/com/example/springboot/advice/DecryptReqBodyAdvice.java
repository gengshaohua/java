package com.example.springboot.advice;

import com.mongodb.lang.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

@Slf4j
@Component
@ControllerAdvice
public class DecryptReqBodyAdvice implements RequestBodyAdvice {

    @Resource
    private HttpServletRequest request;

    private boolean encrypt;

    private Boolean tokenEncrypt;

    private String getUserInfo;

    public DecryptReqBodyAdvice() {
    }

    /**
     * 第一个调用的。判断当前的拦截器（advice是否支持）
     * 注意它的入参有：方法参数、目标类型、所使用的消息转换器等等
     * @param methodParameter
     * @param targetType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 它在body被read读/转换**之前**进行调用的
     * @param inputMessage
     * @param methodParameter
     * @param targetType
     * @param selectedConverterType
     * @return
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> selectedConverterType) {

        return inputMessage;
    }

    /**
     * 它在body体已经转换为Object后执行。so此时都不抛出IOException了嘛~
     * @param body
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    /**
     * 如果body体木有内容就执行这个方法（后面的就不会再执行喽）
     * @param var1
     * @param var2
     * @param var3
     * @param var4
     * @param var5
     * @return
     */
    @Override
    public Object handleEmptyBody(@Nullable Object var1, HttpInputMessage var2, MethodParameter var3, Type var4, Class<? extends HttpMessageConverter<?>> var5) {
        return var1;
    }
}
