/*
package com.baizhi.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Aspect
public class RedisCache {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    //发现key值出现 \xac\xed\x00\x05t\x00\tb，redisTemplate 默认的序列化方式为 jdkSerializeable,
    //StringRedisTemplate的默认序列化方式为StringRedisSerializer

    @Around("execution(* com.baizhi.serviceImpl.ArticleServiceImpl.findAllArticle(..))")
    public Object arount(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StringBuilder stringBuilder = new StringBuilder();

        //修改redisTemplate的序列化方式
        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        //redis中的key值为类名+方法名+方法参数（方法可能有重载，会重复）
        //获取类名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        stringBuilder.append(className);
        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        stringBuilder.append(methodName);
        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            stringBuilder.append(arg);
        }
        String string = stringBuilder.toString();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object result = null;
        //查看当前key---string有没有在内存中
        if(redisTemplate.hasKey(string)){
            System.out.println("这是缓存。。。。。");
            result = valueOperations.get(string);
        }else{
            System.out.println("这是数据库。。。。。");
            result = proceedingJoinPoint.proceed();
            valueOperations.set(string,result);
        }

        return result;
        */
/*System.out.println("--------");
        Object proceed = proceedingJoinPoint.proceed();
        System.out.println(proceed);
        System.out.println("--------");
        return proceed;*//*

    }
    //建设updateArticle为删除方法
    @After("execution(* com.baizhi.serviceImpl.ArticleServiceImpl.updateArticle(..))")
    public void after(JoinPoint joinPoint){
        System.out.println("清空缓存--------");
        Set<String> keys = stringRedisTemplate.keys("*");
        for (String key : keys) {
            stringRedisTemplate.delete(key);
        }
    }

}
*/
