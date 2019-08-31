package com.baizhi.shiro;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Authority;
import com.baizhi.entity.Role;
import com.baizhi.mapper.AdminMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MyRealm extends AuthorizingRealm {
    @Autowired
    AdminMapper adminMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权-------------------");
        //获取主体的主身份
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        Admin admin = adminMapper.login(primaryPrincipal);
        //返回值
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //判断主身份是否为空
        if (primaryPrincipal != null) {
            //给主身份添加角色（角色是从数据库中查询而来）
            List<Role> roleByAdmin = adminMapper.findRoleByAdmin(admin);
            for (Role role : roleByAdmin) {
                authorizationInfo.addRole(role.getName());
            }
            //给主身份添加权限（权限是从数据库中查询而来）
            Set<String> authority = new HashSet<>();
            for (Role role : roleByAdmin) {
                System.out.println(role);
                Set<Authority> authorityByRole = adminMapper.findAuthorityByRole(role);
                for (Authority authority1 : authorityByRole) {
                    System.out.println(authority1);
                    authority.add(authority1.getResource());
                }
            }
            for (String s : authority) {
                System.out.println(s);
                authorizationInfo.addStringPermission(s + ":*");
            }
            //权限的标识解释：
            //"user:add:*" = "资源类型:可以执行的操作:可操作哪个资源实例-某一行"
            //实际只需进行粗粒度的控制，建表只给出id，资源类型-namespace即可，即可执行的操作要么都可以，要么都不可以
            //当前主体可以对user资源类型中所有实例进行添加操作。
            //authorizationInfo.addStringPermission("banner:*");
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证----------------");
        //authenticationToken用户名===principal
        //获取主体的身份信息
        String principal = (String) authenticationToken.getPrincipal();
        //根据principal去数据库查询用户，用来判断该用户是否存在
        Admin admin = adminMapper.login(principal);
        AuthenticationInfo authenticationInfo = null;
        //判断身份信息是否为空
        if (admin != null) {
            authenticationInfo = new SimpleAuthenticationInfo(admin.getUsername(), admin.getPassword(), this.getName());
            //从数据库查询用户的信息-获得用户名和加密过（MD5+salt:qwe+hashIterations散列1024次）的用户密码
            //authenticationInfo = new SimpleAuthenticationInfo("bjt","f4abb7b...", ByteSource.Util.bytes("qwe"),this.getName());
        }
        return authenticationInfo;
    }
}
