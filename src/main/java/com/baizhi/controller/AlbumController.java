package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @RequestMapping("/findAllAlbum")
    @ResponseBody
    public Map<Object, Object> findAllAlbum(Integer page, Integer rows) {//前端传过来的当前页和每页有多少行
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
//        Integer integer = albumService.selectCount();
//        Integer total = integer % rows == 0 ? integer / rows : integer / rows + 1;
//        Map<Object, Object> map = new HashMap<>();
//        List<Album> byPage = albumService.findAllAlbum(page, rows);
//        map.put("rows", byPage);
//        map.put("records", integer);
//        map.put("total", total);
//        map.put("page", page);
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
        return albumService.findAllAlbum(page, rows);
    }

    @ResponseBody
    @RequestMapping("/editAlbum")
    public String editAlbum(Album album, String oper, String[] id) {
        if (oper.equals("add")) {//oper是发送请求时携带的参数
            //添加
            String uuid = UUID.randomUUID().toString().replace("-", "");
            album.setId(uuid);
            album.setScore(0);
            album.setCount(0);
            album.setPublish_date(new Date());
            albumService.insertAlbum(album);
            return uuid;
        } else if (oper.equals("del")) {
            //删除
            //判断该专辑下有无章节

            albumService.deleteAlbum(id);
        } else if (oper.equals("edit")) {
            //修改 只修改状态
            album.setCover(albumService.findAlbumById(album.getId()).getCover());
            album.setPublish_date(albumService.findAlbumById(album.getId()).getPublish_date());
            albumService.updateAlbum(album);
        }
        return null;
    }

    @RequestMapping("/uploadCover")
    @ResponseBody
    public void uploadCover(String albumId, MultipartFile cover, HttpSession session) {
        Album albumById = albumService.findAlbumById(albumId);
        //获取图片上传的路径
        String realPath = session.getServletContext().getRealPath("/upload/img");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = cover.getOriginalFilename();
        //String newFileName = new Date().getTime() + "_" + filename;
        String newFileName = new Date().getTime() + "_" + filename;//防止重名，重名会发生覆盖
        albumById.setCover(newFileName);
        albumService.updateAlbum(albumById);
        try {
            cover.transferTo(new File(realPath, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
