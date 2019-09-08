package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {
    public Map<Object, Object> findAllArticle(Integer page, Integer rows);

    public void insertArticle(Article article);

    public void updateArticle(Article article);

    public Article findArticleById(String id);

    //public Map<Object, Object> findArticle(String keyword, Integer page, Integer size, String filter);
}
