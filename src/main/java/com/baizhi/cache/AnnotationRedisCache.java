package com.baizhi.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@Aspect
public class AnnotationRedisCache {
    @Autowired
    RedisTemplate redisTemplate;

    @Around("@annotation(com.baizhi.annotation.AddCache)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StringBuilder stringBuilder = new StringBuilder();
        //获取类名-大map的key
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        stringBuilder.append(methodName);
        //获取方法参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            stringBuilder.append(arg);
        }
        String s = stringBuilder.toString();
        HashOperations hashOperations = redisTemplate.opsForHash();
        Object result = null;
        //查看当前key---string有没有在内存中
        if (hashOperations.hasKey(className, s)) {
            System.out.println("缓存。。。。。。");
            result = hashOperations.get(className, s);//获取小map的值
        } else {
            System.out.println("数据库。。。。。。。");
            result = proceedingJoinPoint.proceed();
            hashOperations.put(className, s, result);
        }
        return result;

        /*Object proceed = proceedingJoinPoint.proceed();
        System.out.println(proceed);
        return proceed;*/
    }

    @After("@annotation(com.baizhi.annotation.RemoveCache)")
    public void remove(JoinPoint joinPoint) {
        //获取类名
        String className = joinPoint.getTarget().getClass().getName();
        redisTemplate.delete(className);
        System.out.println("缓存已清空");
    }
}
