package ioc.annotation;

import java.lang.annotation.*;

/**
 * @Classname GPRequestMapping
 * @Description
 * @Date 2021/7/14 21:16
 * @Created by 29681
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestMapping {
    String value() default "";
}
