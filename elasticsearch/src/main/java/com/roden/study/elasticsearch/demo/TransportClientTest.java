package com.roden.study.elasticsearch.demo;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * 这里描述操作elasticsearch采用TransportClient这种方式，官方明确表示在ES 7.0版本中将弃用TransportClient客户端，且在8.0版本中完全移除它.
 * 记录一些常用的方法：https://blog.csdn.net/jatpen/article/details/102632745
 */
public class TransportClientTest {
    private TransportClient client;

    @Before
    public void testBefore() {
        //获取client
        Settings settings= Settings.builder().put("cluster.name","elasticsearch").build();
        client=new PreBuiltTransportClient(settings);
        try {
            client.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("success to connect escluster");
    }

    /**
     * 简单查询es 指定index type id
     */
    @Test
    public void search(){
        // 发起请求得到响应
        GetResponse response=client.prepareGet("lo","user","1").get();
        System.out.println(response.getSource());
    }
    /**
     * 增加文档
     */
    @Test
    public void insert() throws Exception{
        XContentBuilder contentBuilder= XContentFactory.jsonBuilder().startObject()
                .field("createTime",new Date())
                .field("id","1")
                .field("departmentId",2)
                .field("name","小中1号")
                .field("desc","新型冠状病毒感染疫情患者")
                .endObject();
        IndexResponse indexResponse = client.prepareIndex("lo", "user", "10").setSource(contentBuilder).get();
        System.out.println(indexResponse.status());
    }
    /**
     * 删除文档
     */
    @Test
    public void delete() throws Exception{
        DeleteResponse deleteResponse = client.prepareDelete("lo", "user", "10").get();
        System.out.println(deleteResponse.status());
    }
    /**
     * 修改文档
     */
    @Test
    public void update() throws Exception{
        XContentBuilder contentBuilder=XContentFactory.jsonBuilder().startObject()
                .field("id","2")
                .endObject();

        UpdateRequest request=new UpdateRequest();
        request.index("lo").type("user").id("1").doc(contentBuilder);
        UpdateResponse updateResponse = client.update(request).get();
        System.out.println(updateResponse.status());
    }
    /**
     *upsert使用 如有存在对应文档就修改  不存在就新增  需要指定修改的文档 和新增的文档
     */
    @Test
    public void upsert() throws ExecutionException, InterruptedException, IOException {
        XContentBuilder builder=XContentFactory.jsonBuilder()
                .startObject()
                .field("createTime",new Date())
                .field("id","1")
                .field("departmentId",2)
                .field("name","中国生产")
                .endObject();


        IndexRequest indexRequest=new IndexRequest();
        indexRequest.index("lo").type("user").id("1").source(builder);

        UpdateRequest request=new UpdateRequest();
        request.index("lo").type("user").id("1").doc(new XContentFactory().jsonBuilder()
                        .startObject()
                        .field("id","2")
                        .field("name","中国生产")
                        .endObject()
                ).upsert(indexRequest);
        UpdateResponse updateResponse = client.update(request).get();
        System.out.println(updateResponse.status());

    }
    /**
     *
     * 批量查询  multiGet
     */
    @Test
    public  void multiGet() throws Exception{
        MultiGetRequest request=new MultiGetRequest();
        request.add("lo","user","1");
        request.add("lo","user","10");

        MultiGetResponse multiGetItemResponses = client.multiGet(request).get();
        for (MultiGetItemResponse response:multiGetItemResponses) {
            System.out.println(response.getResponse().getSourceAsString());
        }

    }
    /**
     *
     *  bulk实现批量增删改操作。
     */
    @Test
    public void bulk() throws Exception{
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
        IndexRequest request=new IndexRequest();
        request.index("lo").type("user").id("1").source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("createTime",new Date())
                        .field("id","2")
                        .field("departmentId",2)
                        .field("name","小中2号")
                        .endObject());

        IndexRequest request1=new IndexRequest();
        request1.index("lo").type("user").id("10").source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("createTime",new Date())
                        .field("id","3")
                        .field("departmentId",2)
                        .field("name","小中3号")
                        .endObject());
        bulkRequestBuilder.add(request1);
        bulkRequestBuilder.add(request);
        BulkResponse bulkItemResponses = bulkRequestBuilder.get();
        System.out.println(bulkItemResponses.status());

    }
    /**
     *
     * 使用query查询  match_all 查询所有
     */
    @Test
    public void query() throws Exception{
        MatchAllQueryBuilder builder= QueryBuilders.matchAllQuery();
        SearchRequestBuilder index3 = client.prepareSearch("lo").setQuery(builder).setSize(5);
        SearchResponse searchResponse = index3.get();
        SearchHits hits =  searchResponse.getHits();
        for (SearchHit hit:hits) {
            System.out.println(hit.getSourceAsString());
        }
    }
    /**
     *
     * 使用query查询  match 查询
     */
    @Test
    public void matchQuery() throws Exception{
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "小中");
        SearchRequestBuilder index = client.prepareSearch("lo").setQuery(matchQueryBuilder).setSize(10);
        SearchHits hits = index.get().getHits();
        for (SearchHit hit:hits
        ) {
            System.out.println(hit.getSourceAsString());
        }
    }
    /**
     *
     * 使用query查询  mutilMatch 查询
     */
    @Test
    public void mutilMatchQuery() throws Exception{
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("2", "id","name");
        SearchRequestBuilder index = client.prepareSearch("lo").setQuery(multiMatchQueryBuilder).setSize(10);
        SearchHits hits = index.get().getHits();
        for (SearchHit hit:hits
        ) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     *
     * 使用query查询 termheterms查询
     */
    @Test
    public void termsMatchQuery() throws Exception{

        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("name", "2", "20");
        SearchRequestBuilder index = client.prepareSearch("lo").setQuery(termsQueryBuilder).setSize(10);
        SearchHits hits = index.get().getHits();
        for (SearchHit hit:hits
        ) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     *
     * 使用query查询  范围  通配符 前缀 模糊查询
     */
    @Test
    public void query1() throws Exception{
        //范围查询
        //QueryBuilder queryBuilder = QueryBuilders.rangeQuery("id").from("1").to("2");

        //通配符查询 * 零个或多个
        // text的类型是走分词,倒排索引的;keyword的类型需要全字段的精确匹配
        // text结合keyword类型的话 wildcard匹配的时候 后面添加.keyword 否则查询不到

        //QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("name","小*"); // "小中1号"能查询到
        //QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("name","小中*"); // "小中1号"查询不到
        //QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("name.keyword","小中*");//"小中1号"能查询到

        //前缀查询
        //QueryBuilder queryBuilder = QueryBuilders.prefixQuery("name.keyword","小中");

        // 搜索的时候，可能输入的搜索文本会出现误拼写的情况,自动将拼写错误的搜索文本，进行纠正，纠正以后去尝试匹配索引中的数据, 纠正在一定的范围内如果差别大无法搜索出来
        //fuzzy搜索 模糊查询 查询类型的
        QueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("name.keyword","小中0号");

        //id查询
        //QueryBuilder queryBuilder = QueryBuilders.idsQuery().addIds("1","2","4");

        SearchRequestBuilder index = client.prepareSearch("lo").setQuery(queryBuilder).setSize(10);
        SearchHits hits = index.get().getHits();
        for (SearchHit hit:hits
        ) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     *
     * 聚合查询
     */
    @Test
    public void aggregation() throws Exception{
        AggregationBuilder aggregationBuilder= AggregationBuilders.max("max").field("id");
        SearchResponse index3 = client.prepareSearch("lo").addAggregation(aggregationBuilder).get();
        Max max = index3.getAggregations().get("max");
        System.out.println(max.getValue());
    }
    /**
     *
     * queryString
     */
    @Test
    public void queryString() throws Exception{
        // + 代表必须有 -代表没有
        QueryBuilder queryBuilder=QueryBuilders.queryStringQuery("+中 -国");
        SearchRequestBuilder index3 = client.prepareSearch("lo")
                .setQuery(queryBuilder)
                .setSize(10);
        SearchResponse searchResponse = index3.get();
        for (SearchHit hit:searchResponse.getHits()) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     *
     * 组合查询
     */
    @Test
    public void boolQuery() throws Exception{
        QueryBuilder queryBuilder=QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("departmentId",2))
                .mustNot(QueryBuilders.matchQuery("name.keyword","小中2号"))
                .should(QueryBuilders.matchQuery("name.keyword","小中3号"))
                .filter(QueryBuilders.rangeQuery("id").gte("1"));


        SearchRequestBuilder index3 = client.prepareSearch("lo")
                .setQuery(queryBuilder)
                .setSize(10);
        SearchResponse searchResponse = index3.get();
        for (SearchHit hit:searchResponse.getHits()) {
            System.out.println(hit.getSourceAsString());
        }
    }


}
