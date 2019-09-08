package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
@RestController     //当前控制器的所有方法均返回json
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @RequestMapping("/findAllArticle")
    public Map<Object, Object> findAllArticle(Integer page, Integer rows) {
        return articleService.findAllArticle(page, rows);
    }
    /*@RequestMapping("/findArticle")
    public Map<Object, Object> findArticle(String keyword, Integer page, Integer size, String filter){
        Map<Object, Object> article = articleService.findArticle(keyword, page, size, filter);
        return article;
    }*/

    @RequestMapping("/editArticle")
    public void editArticle(Article article, String oper) {
        if (oper.equals("add")) {
            //添加
            String id = UUID.randomUUID().toString().replace("-", "");
            /*Integer orderId=UUID.randomUUID().toString().hashCode();//UUID转换成纯数字
            orderId = orderId < 0 ? -orderId : orderId; //String.hashCode() 值会为空
            return orderId;*/
            articleService.insertArticle(article);
        } else if (oper.equals("del")) {
            //删除
        } else if (oper.equals("edit")) {
            //编辑-修改
            articleService.updateArticle(article);
        }
    }

    @RequestMapping("/addArticle")
    public void addArticle(Article article) {
        System.out.println("article=======" + article);
        //缺少id,publish_date
        String uuid = UUID.randomUUID().toString().replace("-", "");
        article.setId(uuid);
        article.setPublish_date(new Date());
        articleService.insertArticle(article);
    }

    @RequestMapping("/updateArticle")
    public void updateArticle(Article article) {//article包含id，title，author，content，status
        //只修改content和status
        articleService.updateArticle(article);
    }

}
