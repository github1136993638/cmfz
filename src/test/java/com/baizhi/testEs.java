/*
package com.baizhi;

import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class testEs {
    private TransportClient transportClient;

    //执行所有的测试方法之前均会先执行@Before注解修饰的方法
    @Before
    public void before() throws UnknownHostException {
        this.transportClient = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.244.231"),9300));
    }

    @Test
    public void testIndex() throws UnknownHostException, InterruptedException, ExecutionException {
        //指定es的ip
        InetAddress inetAddress = InetAddress.getByName("192.168.244.231");
        //指定es的ip和端口号
        TransportAddress transportAddress = new TransportAddress(inetAddress,9300);
        //获取es的操作客户端
        TransportClient transportClient = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(transportAddress);

        //创建索引 以管理员操作索引
        CreateIndexResponse ems = transportClient.admin().indices().prepareCreate("ems").execute().get();
        System.out.println(ems.index());

    }

    //创建索引类型并且指定映射
    @Test
    public void testCreatIndexTypeMapping() throws IOException, ExecutionException, InterruptedException {

        CreateIndexResponse ems = transportClient.admin().indices().prepareCreate("ems").execute().get();

        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
        xContentBuilder.startObject()
                .startObject("properties")
                .startObject("name")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .field("search_analyzer","ik_max_word")
                .endObject()
                .startObject("age")
                .field("type", "integer")
                .endObject()
                .startObject("sex")
                .field("type", "keyword")
                .endObject()
                .startObject("content")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .field("search_analyzer","ik_max_word")
                .endObject()//关的content对象
                .endObject()//关的properties对象
                .endObject();//关的整个对象，最外层的

        PutMappingRequest putMappingRequest = new PutMappingRequest().type("emp").source(xContentBuilder);

        transportClient.admin().indices().putMapping(putMappingRequest).get();

    }

    //增删改查
    @Test
    public void testInsert() throws IOException, ExecutionException, InterruptedException {

        //构建json对象
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
        xContentBuilder.startObject()
                .field("name","白敬亭")
                .field("age",18)
                .field("sex","男")
                .field("content","不加糖也很甜")
                .endObject();
        //将json对象以文档的形式添加到es索引库
        IndexResponse indexResponse = transportClient.prepareIndex("ems", "emp").setSource(xContentBuilder).execute().get();
        System.out.println(indexResponse.status());//CREATED

    }

    @Test
    public void testUpdate() throws ExecutionException, InterruptedException, IOException {

        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
        xContentBuilder.startObject()
                .field("age",28)
                .endObject();
        UpdateResponse updateResponse = transportClient.prepareUpdate("ems", "emp", "vKmbcWwBakylUvrZXQNe").setDoc(xContentBuilder).execute().get();
        System.out.println(updateResponse.status());
    }

    @Test
    public void testDelete() throws ExecutionException, InterruptedException {

        DeleteResponse deleteResponse = transportClient.prepareDelete("ems","emp","vKmbcWwBakylUvrZXQNe").execute().get();
        System.out.println(deleteResponse.status());
    }

    //只能查询一条数据
    @Test
    public void testQuery() throws ExecutionException, InterruptedException {

        GetResponse documentFields = transportClient.prepareGet("ems", "emp", "vamqcWwBakylUvrZ4QMG").execute().get();

        //拿到的是一个map对象---要展示到页面的内容
        Map<String,Object> source = documentFields.getSource();
        //拿到的是json字符串
        Map<String,Object> sourceAsMap = documentFields.getSourceAsMap();

        System.out.println(documentFields);//整个对象---一条记录信息
        System.out.println(documentFields.getSource());
    }

    //批量操作
    @Test
    public void testBulk() throws IOException, ExecutionException, InterruptedException {

        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
        xContentBuilder.startObject()
                .field("name","盛明兰")
                .field("age",16)
                .field("sex","女")
                .field("content","知否知否")
                .endObject();
        IndexRequest indexRequest = new IndexRequest("ems","emp","1");
        indexRequest.source(xContentBuilder);

        XContentBuilder xContentBuilder2 = XContentFactory.jsonBuilder();
        xContentBuilder2.startObject()
                .field("name","顾廷烨")
                .field("age",26)
                .field("sex","男")
                .field("content","知否知否")
                .endObject();
        IndexRequest indexRequest2 = new IndexRequest("ems","emp","2");
        indexRequest2.source(xContentBuilder2);

        DeleteRequest deleteRequest = new DeleteRequest("ems","emp","vamqcWwBakylUvrZ4QMG");

        XContentBuilder xContentBuilder3 = XContentFactory.jsonBuilder();
        xContentBuilder3.startObject().field("age",28).endObject();
        UpdateRequest updateRequest = new UpdateRequest("ems","emp","2");
        updateRequest.doc(xContentBuilder3);

        transportClient.prepareBulk().add(indexRequest).add(indexRequest2).add(deleteRequest).add(updateRequest).execute().get();

    }

    //查询所有并排序
    @Test
    public void testQueryAll() throws ExecutionException, InterruptedException {

        SearchResponse searchResponse = transportClient.prepareSearch("ems").setTypes("emp").setQuery(QueryBuilders.matchAllQuery()).addSort("age", SortOrder.DESC).execute().get();

        SearchHits hits = searchResponse.getHits();
        System.out.println("符合条件的记录数===" + hits.totalHits);
        for (SearchHit hit : hits) {
            System.out.print("当前索引的分数: "+hit.getScore());
            System.out.print(", 对应结果:=====>"+hit.getSourceAsString());
            System.out.println(", 指定字段结果:"+hit.getSourceAsMap().get("name"));
            System.out.println("=================================================");
        }
    }

    //高亮查询
    */
/**
 * 高亮查询
 * .highlighter(highlightBuilder) 用来指定高亮设置
 * requireFieldMatch(false) 开启多个字段高亮
 * field 用来定义高亮字段
 * preTags("<span style='color:red'>")  用来指定高亮前缀
 * postTags("</span>") 用来指定高亮后缀
 *//*

    @Test
    public void testHighlight() throws ExecutionException, InterruptedException {

        //设置查询关键词
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name","顾");
        //设置高亮展示查询关键词
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false).field("name").field("content").preTags("<span style='color:red'>").postTags("</span>");
        SearchResponse searchResponse = transportClient.prepareSearch("ems").setTypes("emp")
                .highlighter(highlightBuilder).setQuery(termQueryBuilder).execute().get();

        SearchHits hits = searchResponse.getHits();
        System.out.println("符合条件的记录数：" + hits.getTotalHits());

        for (SearchHit hit : hits) {
            */
/*存储所有字段的高亮结果*//*

            // name  name高亮结果
            //content content的高亮结果
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            for(String s:sourceAsMap.keySet()){
                //如果高亮则展示高亮内容 否则展示原始内容
                HighlightField highlightField = highlightFields.get(s);
                if (highlightField==null){
                    System.out.println("===原始内容===");
                    System.out.println(s + "======" + sourceAsMap.get(s));
                }else {
                    System.out.println("======高亮之后======");
                    String s1 = highlightField.getFragments()[0].toString();
                    System.out.println(s + "======" + s1);
                }
            }
        }
    }
}
*/
