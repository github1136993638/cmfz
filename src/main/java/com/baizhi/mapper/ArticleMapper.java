package com.baizhi.mapper;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {

    public List<Article> findAllArticle(@Param("start") Integer start, @Param(value = "rows") Integer rows);

    public Integer selectCount();

    public void insertArticle(Article article);

    public void updateArticle(Article article);

    public Article findArticleById(String id);

}
