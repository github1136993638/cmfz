package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    public Map<Object, Object> findAllArticle(Integer page, Integer rows);

    public void insertArticle(Article article);

    public void updateArticle(Article article);

    public Article findArticleById(String id);

    public List<Article> findArticle(String val);
}
