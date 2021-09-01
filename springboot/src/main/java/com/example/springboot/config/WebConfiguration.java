package com.example.springboot.config;

import com.example.springboot.intercepter.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author gengshaohua.ext
 * @date 2021/9/1
 * @apiNote
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**");
    }
}
