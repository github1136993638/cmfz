package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.FilenameUtils;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

@Controller
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    ChapterService chapterService;

    @ResponseBody
    @RequestMapping("/findAllChapter")
    public Map<Object, Object> findAllChapter(Integer page, Integer rows, String albumId) {
        return chapterService.findAllChapter(page, rows, albumId);
    }

    @ResponseBody
    @RequestMapping("/editChapter")
    public String editChapter(Chapter chapter, String oper, String[] id, String albumId) {
        //Album albumById = albumService.findAlbumById(albumId);
        if (oper.equals("add")) {//oper是发送请求时携带的参数
            //添加
            return chapterService.insertChapter(chapter, albumId);
        } else if (oper.equals("del")) {
            //删除 多选时，浏览器返回的是名叫id的数组:String[] id
            /*
             * Form Data:
             * oper: del
             * id: 36f4b6222a7d423ca4b09ce4c01fe50a,e29b43b93b6a483bb5d6573da347244f
             * */
            /*chapterService.deleteChapter(id);
            if (id.length>1){
                albumById.setCount(albumById.getCount()-id.length);
            }
            if(albumById.getCount()>0){
                albumById.setCount(albumById.getCount()-1);
            }
            albumService.updateAlbum(albumById);*/
        } else if (oper.equals("edit")) {
            /*
             * Form Data:
             * status: 不展示
             *audio:
             *oper: edit
             *id: ed6495c9035044b78b4c87867867077d
             * */
            //只修改状态字段---updateChapter方法只修改状态字段
            chapterService.updateChapter(chapter);
        }
        return null;
    }

    @RequestMapping("/uploadAudio")
    @ResponseBody
    public void uploadAudio(String chapterId, MultipartFile audio, HttpSession session) {
        Chapter chapterById = chapterService.findChapterById(chapterId);

        try {
            chapterService.updateChapterFile(chapterById, audio, session);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
        } catch (CannotReadException e) {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
        }
    }

    //audio控件自带下载功能
    @RequestMapping("/download")
    public void download(String id, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Chapter chapterById = chapterService.findChapterById(id);
        //根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/audio");
        //拼接获取文件的路径
        String filePath = realPath + "/" + chapterById.getAudio();
        //根据文件路径获取指定文件
        File file = new File(filePath);
        //将文件读取为输入流
        FileInputStream fis = new FileInputStream(file);
        //获取输出流，响应输入流
        ServletOutputStream outputStream = response.getOutputStream();
        //处理在线打开问题  文件名字不存在问题   处理中文文件名不显示问题
        response.setHeader("content-Disposition", "attachment;fileName=" + URLEncoder.encode(chapterById.getAudio(), "utf-8").replace("+", "%20"));
        //%20:代表空格  encode.replace("+","%20");---解决文件名中有空格，会自动转换成+号
        //文件拷贝  将输入流内容读出来  写到输出流中
        //FileUtils.copyFile(file,outputStream);//使用FileUtils工具类文件拷贝
        int len = 0;
        byte[] bytes = new byte[1024];
        while (true) {
            len = fis.read(bytes);
            if (len == -1) break;
            outputStream.write(bytes, 0, len);
        }

        //建议返回响应类型，动态获取文件类型---不是必须的
        //根据文件名获取文件的后缀 jpg
        String extension = FilenameUtils.getExtension(chapterById.getAudio());
        //根据后缀名获取文件类型
        String mimeType = request.getSession(true).getServletContext().getMimeType(extension);
        response.setContentType(mimeType);
    }

}
