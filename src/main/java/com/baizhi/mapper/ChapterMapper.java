package com.baizhi.mapper;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterMapper {

    public List<Chapter> findAllChapter(@Param("start") Integer start, @Param("rows") Integer rows, @Param("albumId") String albumId);

    public Integer selectCount(String aid);

    public void insertChapter(Chapter chapter);

    public void deleteChapter(String[] id);

    public void updateChapter(Chapter chapter);

    public void updateChapterFile(Chapter chapter);

    public Chapter findChapterById(String id);

}
