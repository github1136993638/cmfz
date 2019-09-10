package com.baizhi.serviceImpl;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.RemoveCache;
import com.baizhi.entity.Article;
import com.baizhi.mapper.ArticleMapper;
import com.baizhi.repository.ArticleRepository;
import com.baizhi.service.ArticleService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    @AddCache
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
    @RemoveCache
    public void insertArticle(Article article) {
        articleMapper.insertArticle(article);
        //为新添加的文章创建索引
        articleRepository.save(article);
    }

    @RemoveCache
    @Override
    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
    }
    @Override
    public Article findArticleById(String id) {
        return articleMapper.findArticleById(id);
    }

    @Override
    public List<Article> findArticle(String val) {
        //构建高亮的相关属性
        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        field.preTags("<span style='color:red'>");
        field.postTags("</span>");
        field.requireFieldMatch();
        //es查询语句。
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                //多字段根据val的值查询title字段和content字段，使用ik分词器
                .withQuery(QueryBuilders.queryStringQuery(val).analyzer("ik_max_word").field("title").field("content"))
                .withHighlightFields(field)
                .build();
        AggregatedPage<Article> articles = elasticsearchTemplate.queryForPage(build, Article.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<Article> list = new ArrayList<>();
                SearchHit[] hits = searchResponse.getHits().getHits();
                for (SearchHit hit : hits) {
                    System.out.println("hit=======" + hit);
                    //获取源数据
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    Article article = new Article();//可以根据id查询出来一个article对象
                    article.setId(sourceAsMap.get("id").toString());
                    article.setTitle(sourceAsMap.get("title").toString());
                    article.setStatus(sourceAsMap.get("status").toString());
                    try {
                        article.setPublish_date(new SimpleDateFormat("yyyy-MM-dd").parse(sourceAsMap.get("publish_date").toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    article.setAuthor(sourceAsMap.get("author").toString());
                    article.setContent(sourceAsMap.get("content").toString());

                    //用高亮的字段替换源数据的字段
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    if (highlightFields.get("title") != null) {
                        article.setTitle(highlightFields.get("title").getFragments()[0].toString());
                    }
                    if (highlightFields.get("content") != null) {
                        article.setContent(highlightFields.get("content").getFragments()[0].toString());
                    }
                    list.add(article);
                    System.out.println(list.toString());
                }
                return new AggregatedPageImpl<T>((List<T>) list);
            }
        });
        return articles.getContent();
    }
}
