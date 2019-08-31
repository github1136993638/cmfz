package com.baizhi.serviceImpl;

import com.baizhi.entity.Admin;
import com.baizhi.mapper.AdminMapper;
import com.baizhi.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> login(String username, String password, String enCode, HttpSession session) {

        Map<String, Object> map = new HashMap<>();
        Admin admin = adminMapper.login(username);
        //获得主体
        Subject subject = SecurityUtils.getSubject();
        //获得令牌
        AuthenticationToken token = new UsernamePasswordToken(username, password);//前台传递过来的参数
        //将令牌给到主体，去进行认证
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            map.put("loginError", "用户名输入错误！");
            return map;
        } catch (IncorrectCredentialsException i) {
            i.printStackTrace();
            map.put("loginError", "密码输入错误！");
            return map;
        }
        //先判断验证码，如果验证码不符合，可以省去后边连接数据库查询的步骤
        if (!enCode.equals(session.getAttribute("imgCode"))) {
            map.put("loginError", "验证码输入错误！");
            return map;
        } else {
            map.put("admin", admin);
            return map;
        }

    }
}
