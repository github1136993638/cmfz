package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public interface ChapterService {
    public Map<Object, Object> findAllChapter(Integer page, Integer rows, String albumId);

    public String insertChapter(Chapter chapter, String albumId);

    //public void deleteChapter(String[] id);
    public void updateChapter(Chapter chapter);

    public void updateChapterFile(Chapter chapter, MultipartFile audio, HttpSession session) throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException;

    public Chapter findChapterById(String id);
}
