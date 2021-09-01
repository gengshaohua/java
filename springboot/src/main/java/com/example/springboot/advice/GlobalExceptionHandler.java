package com.example.springboot.advice;

import com.alibaba.fastjson.JSON;
import com.example.springboot.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
    }

    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public R validationBodyException(MethodArgumentNotValidException exception) {
        StringBuffer buffer = new StringBuffer();
        BindingResult result = exception.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach((p) -> {
                FieldError fieldError = (FieldError)p;
                log.error("Data check failure : object{" + fieldError.getObjectName() + "},field{" + fieldError.getField() + "},errorMessage{" + fieldError.getDefaultMessage() + "}");
                buffer.append(fieldError.getDefaultMessage()).append(",");
            });
        }

        R error = R.error(9999, buffer.toString().substring(0, buffer.toString().length() - 1));
        log.error("validationBodyException 结果：{}", JSON.toJSONString(error));
        return error;
    }

    @ExceptionHandler({Throwable.class})
    public Map<String, Object> handleThrowable(Throwable e, HttpServletRequest request) {
        Map<String, Object> error = new HashMap();
        error.put("code", "9999");
        error.put("msg", e.getMessage());
        log.error("URL:{} ,系统异常: ", request.getRequestURI(), e);
        return error;
    }

    @ExceptionHandler({BindException.class})
    public Map<String, Object> exceptionHandler(BindException e, HttpServletRequest request) {
        String failMsg = e.getBindingResult().getFieldError().getDefaultMessage();
        Map<String, Object> error = new HashMap();
        error.put("code", "1001");
        error.put("msg", failMsg);
        log.error("URL:{} ,绑定异常:{} ", request.getRequestURI(), failMsg);
        return error;
    }

}
