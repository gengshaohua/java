package ioc.annotation;

import java.lang.annotation.*;

/**
 * @Classname GPRequestMapping
 * @Description 参数映射
 * @Date 2021/7/14 21:14
 * @Created by 29681
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestParam {
    String value() default "";
}
