package annotation;

import java.lang.annotation.*;

/**
 * @Classname GPController
 * @Description controller
 * @Date 2021/7/14 21:12
 * @Created by 29681
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPController {
    String value() default "";
}
