package com.baizhi.serviceImpl;

import com.baizhi.entity.Article;
import com.baizhi.mapper.ArticleMapper;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<Object, Object> findAllArticle(Integer page, Integer rows) {
        Map<Object, Object> map = new HashMap<>();
        //total总页数 page第几页 records总记录数 rows分页之后的数据
        Integer integer = articleMapper.selectCount();
        int total = integer % rows == 0 ? integer / rows : integer / rows + 1;
        Integer start = (page - 1) * rows;//page从1开始，start从0开始
        List<Article> byPage = articleMapper.findAllArticle(start, rows);
        map.put("rows", byPage);
        map.put("records", integer);
        map.put("total", total);
        map.put("page", page);
        return map;
    }

    @Override
    public void insertArticle(Article article) {
        articleMapper.insertArticle(article);
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
    }

    @Override
    public Article findArticleById(String id) {
        return null;
    }
}
