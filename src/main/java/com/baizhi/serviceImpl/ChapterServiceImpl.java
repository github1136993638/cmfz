package com.baizhi.serviceImpl;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.mapper.ChapterMapper;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterMapper chapterMapper;
    @Autowired
    AlbumService albumService;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<Object, Object> findAllChapter(Integer page, Integer rows, String albumId) {

        Map<Object, Object> map = new HashMap<>();
        //total总页数 page第几页 records总记录数 rows分页之后的数据
        Integer integer = chapterMapper.selectCount(albumId);//总共有多少条数据
        Integer total = integer % rows == 0 ? integer / rows : integer / rows + 1;//total取？前后的值
        Integer start = (page - 1) * rows;//page从1开始，start从0开始
        List<Chapter> byPage = chapterMapper.findAllChapter(start, rows, albumId);
        map.put("rows", byPage);
        map.put("records", integer);
        map.put("total", total);
        map.put("page", page);
        return map;
    }

    @Override
    public String insertChapter(Chapter chapter, String albumId) {
        Album albumById = albumService.findAlbumById(albumId);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        chapter.setId(uuid);
        chapter.setCreate_date(new Date());
        chapter.setAid(albumId);
        albumById.setCount(albumById.getCount() + 1);
        albumService.updateAlbum(albumById);
        chapterMapper.insertChapter(chapter);
        return uuid;
    }

    /*@Override
    public void deleteChapter(String[] id) {
        chapterMapper.deleteChapter(id);
    }*/

    @Override
    public void updateChapter(Chapter chapter) {
        chapterMapper.updateChapter(chapter);
    }

    @Override
    public void updateChapterFile(Chapter chapter, MultipartFile audio, HttpSession session) throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {

        //根据相对路径获取文件上传的绝对路径
        String realPath = session.getServletContext().getRealPath("/upload/audio");
        //创建文件路径
        File file = new File(realPath);//音频文件所在目录
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = audio.getOriginalFilename();//获取原始文件名字
        String newFileName = new Date().getTime() + "_" + filename;
        //根据文件路径获取指定文件
        File audioFile = new File(realPath, newFileName);//realPath路径下的名为newFileName文件
        audio.transferTo(audioFile);//拷贝原始文件到audioFile文件

        //根据id将新文件名、大小、音频时间进行修改
        //根据获取音频大小
        long size = audio.getSize();
        DecimalFormat format = new DecimalFormat("0.00");
        String str = String.valueOf(size);
        Double dd = Double.valueOf(str) / 1024 / 1024;
        String sizess = format.format(dd) + "MB";

        //获取文件时长   分
        AudioFile audioFileIO = null;//用AudioFileIO读取音频文件
        audioFileIO = AudioFileIO.read(audioFile);
        AudioHeader audioHeader = audioFileIO.getAudioHeader();
        int length = audioHeader.getTrackLength();//单位是分钟
        String duration = length / 60 + "分" + length % 60 + "秒";

        chapter.setAudio(newFileName);
        chapter.setSize(sizess);
        chapter.setDuration(duration);

        chapterMapper.updateChapterFile(chapter);
    }

    @Override
    public Chapter findChapterById(String id) {
        return chapterMapper.findChapterById(id);
    }
}
