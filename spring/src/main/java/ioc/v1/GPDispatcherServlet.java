package ioc.v1;

import ioc.annotation.GPAutowired;
import ioc.annotation.GPController;
import ioc.annotation.GPRequestMapping;
import ioc.annotation.GPService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname GPDispatcherServlet
 * @Description 转发器
 * @Date 2021/7/14 21:19
 * @Created by 29681
 */
public class GPDispatcherServlet extends HttpServlet {

    private ConcurrentHashMap<String, Object> beans = new ConcurrentHashMap<>();

    private Map<String, Object> handlerMapping = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        if(!this.handlerMapping.containsKey(url)){resp.getWriter().write("404 Not Found!!");return;}
        Method method = (Method) this.handlerMapping.get(url);
        Map<String,String[]> params = req.getParameterMap();
        method.invoke(this.handlerMapping.get(method.getDeclaringClass().getName()),new Object[]{req,resp,params.get("name")[0]});
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    /**
     * 初始化容器
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        InputStream is = null;
        try{
            Properties properties = new Properties();
            //1.加载web.contextConfigLocation属性，获取扫描类路径
            is = this.getClass().getClassLoader().getResourceAsStream(config.getInitParameter("contextConfigLocation"));
            properties.load(is);
            String scanPackage = properties.getProperty("scanPackage");
            //2.获取所有类，添加到map中
            doScanner(scanPackage);
            //3.遍历所有类
            for (String className : beans.keySet()) {
                if(!className.contains(".")){continue;}
                Class<?> clazz = Class.forName(className);
                //3.1如果这个类标有GPController注解，获取实例注册到容器中
                if(clazz.isAnnotationPresent(GPController.class)){
                    beans.put(className,clazz.newInstance());
                    String baseUrl = "";
                    //3.1如果这个类标有GPRequestMapping，获取类路径
                    if (clazz.isAnnotationPresent(GPRequestMapping.class)) {
                        GPRequestMapping requestMapping = clazz.getAnnotation(GPRequestMapping.class);
                        baseUrl = requestMapping.value();
                    }
                    //获取这个标有GPController注解的所有方法，判断方法是否有包含GPRequestMapping，进行url注入
                    Method[] methods = clazz.getMethods();
                    for (Method method : methods) {
                        if (!method.isAnnotationPresent(GPRequestMapping.class)) {  continue; }
                        GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);
                        String url = (baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
                        handlerMapping.put(url, method);
                        System.out.println("Mapped " + url + "," + method);
                    }
                }else if(clazz.isAnnotationPresent(GPService.class)){
                    //判断类是否有GPService，注册到容器中
                    GPService service = clazz.getAnnotation(GPService.class);
                    String beanName = service.value();
                    if("".equals(beanName)){beanName = clazz.getName();}
                    Object instance = clazz.newInstance();
                    beans.put(beanName,instance);
                    for (Class<?> i : clazz.getInterfaces()) {
                        beans.put(i.getName(),instance);
                    }
                }else {continue;}
            }
            for (Object object : beans.values()) {
                if(object == null){continue;}
                Class clazz = object.getClass();
                if(clazz.isAnnotationPresent(GPController.class)){
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        if(!field.isAnnotationPresent(GPAutowired.class)){continue; }
                        GPAutowired autowired = field.getAnnotation(GPAutowired.class);
                        String beanName = autowired.value();
                        if("".equals(beanName)){beanName = field.getType().getName();}
                        field.setAccessible(true);
                        try {
                            field.set(beans.get(clazz.getName()),beans.get(beanName));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(is != null){
                try {is.close();} catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.print("GP MVC Framework is init");
    }

    private void doScanner(String scanPackage) throws Exception {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());
        for(File file : classDir.listFiles()){
            if(file.isDirectory()){
                doScanner(scanPackage + "." + file.getName());
            }else{
                if(!file.getName().endsWith(".class")){
                    continue;
                }
                String clazzName = scanPackage + "." + file.getName().replace(".class", "");
                beans.put(clazzName, null);
            }
        }
    }
}
