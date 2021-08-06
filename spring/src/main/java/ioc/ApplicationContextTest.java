package ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Classname ApplicationContextTest
 * @Description
 * @Date 2021/7/17 18:56
 * @Created by 29681
 */
public class ApplicationContextTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");

        System.out.println("ioc容器启动完成");
    }

}
