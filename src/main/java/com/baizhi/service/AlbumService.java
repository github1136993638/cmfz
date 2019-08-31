package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumService {
    public Map<Object, Object> findAllAlbum(Integer page, Integer rows);

    public void insertAlbum(Album album);

    public void deleteAlbum(String[] id);

    public void updateAlbum(Album album);

    public Album findAlbumById(String id);
}
