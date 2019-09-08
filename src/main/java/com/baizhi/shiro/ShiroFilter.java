package com.baizhi.shiro;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroFilter {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        //创建出shiro的工厂
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //把安全管理器设置进shiro工厂
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //在设置shiro的过滤器
        Map<String, String> map = new HashMap<>();
        /*//拦截项目中指定资源
        map.put("/back/*","authc");
        map.put("/album/*","authc");
        map.put("/article/*","authc");
        map.put("/banner/*","authc");
        map.put("/chapter/*","authc");
        map.put("/echarts/*","authc");
        map.put("/kindeditor/*","authc");
        //设置index.jsp为匿名资源，表示谁都可以访问。
        map.put("/index.jsp","anon");
        map.put("/image/*","anon");
        //放行登陆的接口
        map.put("/admin/findAdmin","anon");*/
        //拦截项目中所有资源，只有被认证通过才能访问
        map.put("/**", "authc");
        map.put("/admin/findAdmin", "anon");//放行登陆的接口
        map.put("/image/*", "anon");//放行验证码接口
        //放行静态资源
        map.put("/boot/js/*", "anon");
        map.put("/boot/css/*", "anon");
        map.put("/boot/fonts/*", "anon");
        map.put("/echarts/*", "anon");
        map.put("/jqgrid/*", "anon");
        map.put("/login/assets/**", "anon");
        map.put("/kindeditor/**", "anon");
        map.put("/img/*", "anon");
        //将拦截器的集合设置进过滤器链中。
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        //配置强制登陆的jsp页面的位置和名称
        shiroFilterFactoryBean.setLoginUrl("/login/login.jsp");
        //把shiro工厂返回出去。
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(MyRealm realm) {
        //创建安全管理器，安全管理器需要realm
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realm);
        //获取缓存管理器
        CacheManager cacheManager = new MemoryConstrainedCacheManager();
        //将缓存设置进安全管理器---避免每次判断是否具有某个权限都走后台查询，而是从缓存中取
        securityManager.setCacheManager(cacheManager);
        return securityManager;
    }

    @Bean
    public MyRealm myRealm() {
        //创建出自定义realm的对象。
        MyRealm myRealm = new MyRealm();
        return myRealm;
    }
}
