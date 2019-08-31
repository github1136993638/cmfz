package com.baizhi.controller;

import com.baizhi.entity.EchartsMap;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/findAllUser")
    public Map<Object, Object> findAllUser(Integer page, Integer rows) {
        Map<Object, Object> allUser = userService.findAllUser(page, rows);
        return allUser;  // /index.jsp  视图解析器
    }

    @RequestMapping("/updateUser")
    public void updateUser(User user) {
        userService.updateUser(user);
    }

    @RequestMapping("/regist")
    public void regist(User user) {
        userService.regist(user);
    }

    /*@RequestMapping("/exportUser")
    public void exportUser(HttpServletRequest request, HttpServletResponse response){
        List<User> allUser = userService.findAll();
        for (User user : allUser) {
            String realPath = "http://localhost:8686/cmfz/upload/img/";
            String picture = realPath+"/"+user.getHead_picture();
            user.setHead_picture(picture);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("持明法洲","用户信息表"),User.class,allUser);
        try {
            String fileName="用户信息表.xls";
            //获取响应输出流---往浏览器写（浏览器下载）
            ServletOutputStream outputStream = response.getOutputStream();
            //处理在线打开问题  文件名字不存在问题   处理中文文件名不显示问题
            response.setHeader("content-Disposition","attachment;fileName="+ URLEncoder.encode(fileName,"utf-8").replace("+","%20"));
            //workbook需要输出流导出文件，将响应输出流给workbook
            workbook.write(outputStream);
    }*/
    @RequestMapping("/exportUser")
    public void exportUser(HttpServletRequest request, HttpServletResponse response) {
        userService.exportUser(request, response);
    }

    @RequestMapping("/findAllByProvince")
    public Set<EchartsMap> findAllByProvince() {
        return userService.findAllByProvince();
    }

    /**
     * 获取过去或者未来 任意天内的日期数组
     * intervals天内
     *
     * @return 日期数组
     */
    @RequestMapping("/findAllByDate")
    public List<EchartsMap> findAllByDate() {

        return userService.findAllByDate();
    }


}
