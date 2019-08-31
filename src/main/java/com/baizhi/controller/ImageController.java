package com.baizhi.controller;

import com.baizhi.util.CreateValidateCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@ResponseBody
@RequestMapping("/image")
public class ImageController {

    @RequestMapping("/imageCode")
    public String imageCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        CreateValidateCode createValidateCode = new CreateValidateCode();
        String imgCode = createValidateCode.getCode();

        HttpSession session = request.getSession();
        session.setAttribute("imgCode", imgCode);

        OutputStream outputStream = response.getOutputStream();
        createValidateCode.write(outputStream);
        System.out.println(imgCode);
        return null;
    }

}
