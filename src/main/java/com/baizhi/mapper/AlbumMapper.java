package com.baizhi.mapper;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumMapper {
    public List<Album> findAllAlbum(@Param("start") Integer start, @Param("rows") Integer rows);

    public Integer selectCount();

    public void insertAlbum(Album album);

    public void deleteAlbum(String[] id);

    public void updateAlbum(Album album);

    public Album findAlbumById(String id);
}
