package ioc.annotation;

import java.lang.annotation.*;

/**
 * @Classname GPAutowired
 * @Description 注入
 * @Date 2021/7/14 21:11
 * @Created by 29681
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPAutowired {
    String value() default "";
}
