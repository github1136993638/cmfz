package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    BannerService bannerService;

    @ResponseBody
    @RequestMapping("/findAllBanner")
    public Map<Object, Object> findAllBanner(Integer page, Integer rows) {//前台传过来的当前页和每页有多少行
        /*F12查看发送的请求信息
         * 请求携带的参数
         * _search: false
         *nd: 1565915559098
         *rows: 2
         *page: 2
         *sidx:
         *sord: asc
         * */
        //total总页数 page第几页 records总记录数 rows分页之后的数据
        Integer integer = bannerService.selectCount();
        Integer total = integer % rows == 0 ? integer / rows : integer / rows + 1;
        Map<Object, Object> map = new HashMap<>();
        List<Banner> byPage = bannerService.findAllBanner(page, rows);
        map.put("rows", byPage);
        map.put("records", integer);
        map.put("total", total);
        map.put("page", page);
        /*（控件接收的）分页的数据格式：
         * {
         *   "page":1
         *   "total":20
         *   "records":200
         *   "rows":[
         *   {
         *       "id":1
         *       "name":xiaohuahua
         *   },
         *   {
         *       "id":2
         *       "name":xiaohheihei
         *   }
         * ]
         * }
         * */
        return map;
    }

    @ResponseBody
    @RequestMapping("/editBanner")
    public String editBanner(Banner banner, String oper, String[] id) {
        if (oper.equals("add")) {//oper是发送请求时携带的参数
            //添加
            String uuid = UUID.randomUUID().toString().replace("-", "");
            banner.setId(uuid);
            banner.setCreate_date(new Date());
            bannerService.insertBanner(banner);
            return uuid;
        } else if (oper.equals("del")) {
            //删除 多选时，浏览器返回的是名叫id的数组:String[] id
            /*
             * Form Data:
             * oper: del
             * id: 36f4b6222a7d423ca4b09ce4c01fe50a,e29b43b93b6a483bb5d6573da347244f
             * */
            bannerService.deleteBanner(id);
        } else if (oper.equals("edit")) {
            //修改
            banner.setImg_path(bannerService.findBannerById(banner.getId()).getImg_path());
            banner.setCreate_date(bannerService.findBannerById(banner.getId()).getCreate_date());
            System.out.println(banner.getCreate_date());
            bannerService.updateBanner(banner);
        }
        return null;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public void upload(String bannerId, MultipartFile img_path, HttpSession session) {
        //获取图片上传的路径
        String realPath = session.getServletContext().getRealPath("/upload/img");
        //创建文件路径
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = img_path.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + filename;
        Banner bannerById = bannerService.findBannerById(bannerId);

        bannerById.setImg_path(newFileName);
        bannerService.updateBanner(bannerById);
        try {
            img_path.transferTo(new File(realPath, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
