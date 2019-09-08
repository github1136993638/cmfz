/*
package com.baizhi;

import com.baizhi.entity.Article;
import com.baizhi.mapper.ArticleMapper;
import com.baizhi.repository.ArticleRepository;
import com.baizhi.repository.CustomerArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzApplication.class)
public class TestElasticSearch {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    CustomerArticleRepository customerArticleRepository;
    @Autowired
    ArticleMapper articleMapper;
    @Test
    public void testArticle(){
        articleRepository.save(articleMapper.findArticleById("36ac3cc15ac74781b1782f0641a51375"));
    }
    @Test
    public void testFindArticle(){
        Iterable<Article> all = articleRepository.findAll();
        for (Article article : all) {
            System.out.println(article);
        }
    }
    @Test
    public void testArticleHighlight(){
        List<Article> byHighlight = customerArticleRepository.findByHighlight("盛", 0, 3, "知否");
        for (Article article : byHighlight) {
            System.out.println(article);
        }
    }
}
*/
