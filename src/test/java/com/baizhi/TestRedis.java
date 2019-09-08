package com.baizhi;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ArticleService articleService;

    @Test
    public void test() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name", "bjt");
    }

    @Test
    public void test1() {
        Map<Object, Object> allArticle = articleService.findAllArticle(1, 5);
        //System.out.println(allArticle.toString());
    }

    @Test
    public void test2() {
        Article articleById = articleService.findArticleById("36ac3cc15ac74781b1782f0641a51375");
        articleById.setContent("哈尔!");
        articleService.updateArticle(articleById);
    }

}
