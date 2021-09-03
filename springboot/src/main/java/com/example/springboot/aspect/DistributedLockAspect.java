package com.example.springboot.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.springboot.lock.DistributedLock;
import com.example.springboot.lock.DistributedLockCallback;
import com.example.springboot.lock.DistributedLockTemplate;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class DistributedLockAspect {

    @Autowired
    private DistributedLockTemplate lockTemplate;

    @Pointcut("@annotation(com.example.springboot.lock.DistributedLock)")
    public void DistributedLockAspect() {}

    @Around(value = "DistributedLockAspect()")
    public Object doAround(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        //切点所在的类
        Class targetClass = pjp.getTarget().getClass();
        //使用了注解的方法
        String methodName = pjp.getSignature().getName();
        Class[] parameterTypes = ((MethodSignature)pjp.getSignature()).getMethod().getParameterTypes();
        Method method = targetClass.getMethod(methodName, parameterTypes);
        Object[] arguments = pjp.getArgs();
        final String lockName = getLockName(pjp, method, arguments);
        return lock(pjp, method, lockName);
    }

    @AfterThrowing(value = "DistributedLockAspect()", throwing="ex")
    public void afterThrowing(Throwable ex) {
        throw new RuntimeException(ex);
    }


    public String getLockName(JoinPoint joinPoint, Method method, Object[] args) {
        Objects.requireNonNull(method);
        DistributedLock annotation = method.getAnnotation(DistributedLock.class);
        String lockName = getAnnotationValue(joinPoint, annotation.lockName());
        String param = annotation.param();
        if (isEmpty(lockName) && args.length > 0) {
            if (isNotEmpty(param)) {
                Object arg;
                if (annotation.argNum() > 0) {
                    arg = args[annotation.argNum() - 1];
                } else {
                    arg = args[0];
                }
                lockName = String.valueOf(getParam(arg, param));
            } else if (annotation.argNum() > 0) {
                lockName = args[annotation.argNum() - 1].toString();
            }
        }

        if (isNotEmpty(lockName)) {
            String preLockName = annotation.lockNamePre();
            String postLockName = annotation.lockNamePost();
            String separator = annotation.separator();
            StringBuilder lName = new StringBuilder();
            if (isNotEmpty(preLockName)) {
                lName.append(preLockName).append(separator);
            }
            lName.append(lockName);
            if (isNotEmpty(postLockName)) {
                lName.append(separator).append(postLockName);
            }
            lockName = lName.toString();
            return lockName;
        }
        throw new IllegalArgumentException("Can't get or generate lockName accurately!");
    }

    /**
     * 从方法参数获取数据
     *
     * @param param
     * @param arg 方法的参数数组
     * @return
     */
    public Object getParam(Object arg, String param) {
        if (isNotEmpty(param) && arg != null) {
            try {
                return PropertyUtils.getProperty(arg, param);
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(arg + "没有属性" + param + "或未实现get方法。", e);
            } catch (Exception e) {
                throw new RuntimeException("", e);
            }
        }
        return null;
    }

    public Object lock(ProceedingJoinPoint pjp, Method method, final String lockName) {
        DistributedLock annotation = method.getAnnotation(DistributedLock.class);
        boolean fairLock = annotation.fairLock();
        boolean tryLock = annotation.tryLock();
        boolean openWatchDog = annotation.isOpenWatchDog();
        if (tryLock) {
            return tryLock(openWatchDog, pjp, annotation, lockName, fairLock);
        } else {
            return lock(openWatchDog, pjp,annotation, lockName, fairLock);
        }
    }

    public Object lock(boolean isOpenWatchDog, ProceedingJoinPoint pjp, DistributedLock annotation,
                       final String lockName, boolean fairLock) {
        if(isOpenWatchDog){
           return lockTemplate.lockWatchDog(new DistributedLockCallback<Object>() {
                @Override
                public Object process() {
                    return proceed(pjp);
                }
                @Override
                public String getLockName() {
                    return lockName;
                }
            }, fairLock);
        }else{
            long leaseTime = annotation.leaseTime();
            TimeUnit timeUnit = annotation.timeUnit();
            return lockTemplate.lock(new DistributedLockCallback<Object>() {
                @Override
                public Object process() {
                    return proceed(pjp);
                }
                @Override
                public String getLockName() {
                    return lockName;
                }
            }, leaseTime, timeUnit, fairLock);
        }
    }

    public Object tryLock(boolean isOpenWatchDog, ProceedingJoinPoint pjp, DistributedLock annotation,
                          final String lockName,
                          boolean fairLock) {
        long waitTime = annotation.waitTime();
        long leaseTime = annotation.leaseTime();
        TimeUnit timeUnit = annotation.timeUnit();
        if(isOpenWatchDog){
            return lockTemplate.tryLockWatchDog(new DistributedLockCallback<Object>() {
                @Override
                public Object process() {
                    return proceed(pjp);
                }
                @Override
                public String getLockName() {
                    return lockName;
                }
            }, waitTime, timeUnit, fairLock);
        }else{
            return lockTemplate.tryLock(new DistributedLockCallback<Object>() {
                @Override
                public Object process() {
                    return proceed(pjp);
                }
                @Override
                public String getLockName() {
                    return lockName;
                }
            }, waitTime, leaseTime, timeUnit, fairLock);
        }


    }

    public Object proceed(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    /**
     * 获取注解中传递的动态参数的参数值
     *
     * @param joinPoint
     * @param name
     * @return
     */
    public String getAnnotationValue(JoinPoint joinPoint, String name) {
        String paramName = name;
        // 获取方法中所有的参数
        Map<String, Object> params = getParams(joinPoint);
        // 参数是否是动态的:#{paramName}
        if (paramName.matches("^#\\{\\D*\\}")) {
            // 获取参数名
            paramName = paramName.replace("#{", "").replace("}", "");
            // 是否是复杂的参数类型:对象.参数名
            if (paramName.contains(".")) {
                String[] split = paramName.split("\\.");
                // 获取方法中对象的内容
                Object object = getValue(params, split[0]);
                // 转换为JsonObject
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
                // 获取值
                Object o = jsonObject.get(split[1]);
                return String.valueOf(o);
            }
            // 简单的动态参数直接返回
            return String.valueOf(getValue(params, paramName));
        }
        // 非动态参数直接返回
        return name;
    }

    /**
     * 获取方法的参数名和值
     *
     * @param joinPoint
     * @return
     */
    public Map<String, Object> getParams(JoinPoint joinPoint) {
        Map<String, Object> params = new HashMap<>(8);
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] names = signature.getParameterNames();
        for (int i = 0; i < args.length; i++) {
            params.put(names[i], args[i]);
        }
        return params;
    }

    /**
     * 根据参数名返回对应的值
     *
     * @param map
     * @param paramName
     * @return
     */
    public Object getValue(Map<String, Object> map, String paramName) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals(paramName)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    private boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }
}