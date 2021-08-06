package annotation;

import java.lang.annotation.*;

/**
 * @Classname GPService
 * @Description 注入
 * @Date 2021/7/14 21:17
 * @Created by 29681
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPService {
    String value() default "";
}
