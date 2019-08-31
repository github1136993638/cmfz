package com.baizhi.controller;

import com.baizhi.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping("/findAdmin")
    @ResponseBody
    public Map<String, Object> findAdmin(String username, String password, String enCode, HttpSession session) {

        Map<String, Object> admin = adminService.login(username, password, enCode, session);
        return admin;
    }

    @RequestMapping("/exit")
    public String exit(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        //shiro封装的用户退出方法
        subject.logout();
        return "redirect:/login/login.jsp";

    }

}
