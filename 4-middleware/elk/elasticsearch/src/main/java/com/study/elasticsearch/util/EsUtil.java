package com.study.elasticsearch.util;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
@Slf4j
public class EsUtil {

    @Resource
    private RestHighLevelClient restHighLevelClient;


    /**
     * 创建索引(默认分片数为5和副本数为1)
     * @param indexName
     * @throws IOException
     */
    public boolean createIndex(String indexName) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(Settings.builder()
                // 设置分片数为3， 副本为2
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        request.mapping(generateBuilder());
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        // 指示是否所有节点都已确认请求
        boolean acknowledged = response.isAcknowledged();
        // 指示是否在超时之前为索引中的每个分片启动了必需的分片副本数
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        if (acknowledged || shardsAcknowledged) {
            log.info("创建索引成功！索引名称为{}", indexName);
            return true;
        }
        return false;
    }



    /**
     * 判断索引是否存在
     * @param indexName
     * @return
     */
    public boolean isIndexExists(String indexName){
        boolean exists = false;
        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
            getIndexRequest.humanReadable(true);
            exists = restHighLevelClient.indices().exists(getIndexRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exists;
    }

    /**
     * 删除索引
     * @param indexName
     * @return
     */
    public boolean delIndex(String indexName){
        boolean acknowledged = false;
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
            deleteIndexRequest.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            acknowledged = delete.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acknowledged;
    }



//    /**
//     * 更新索引(默认分片数为5和副本数为1)：
//     * 只能给索引上添加一些不存在的字段
//     * 已经存在的映射不能改
//     *
//     * @param clazz 根据实体自动映射es索引
//     * @throws IOException
//     */
//    public boolean updateIndex(Class clazz) throws Exception {
//
//        Document declaredAnnotation = (Document )clazz.getDeclaredAnnotation(Document.class);
//        if(declaredAnnotation == null){
//            throw new Exception(String.format("class name: %s can not find Annotation [Document], please check", clazz.getName()));
//        }
//        String indexName = declaredAnnotation.index();
//        PutMappingRequest request = new PutMappingRequest(indexName);
//
//        request.source(generateBuilder(clazz));
//        AcknowledgedResponse response = restHighLevelClient.indices().putMapping(request, RequestOptions.DEFAULT);
//        // 指示是否所有节点都已确认请求
//        boolean acknowledged = response.isAcknowledged();
//
//        if (acknowledged ) {
//            log.info("更新索引索引成功！索引名称为{}", indexName);
//            return true;
//        }
//        return false;
//    }


    /**
     * 添加单条数据
     * 提供多种方式：
     *  1. json
     *  2. map
     *      Map<String, Object> jsonMap = new HashMap<>();
     *      jsonMap.put("user", "kimchy");
     *      jsonMap.put("postDate", new Date());
     *      jsonMap.put("message", "trying out Elasticsearch");
     *      IndexRequest indexRequest = new IndexRequest("posts")
     *          .id("1").source(jsonMap);
     *  3. builder
     *      XContentBuilder builder = XContentFactory.jsonBuilder();
     *      builder.startObject();
     *      {
     *          builder.field("user", "kimchy");
     *          builder.timeField("postDate", new Date());
     *          builder.field("message", "trying out Elasticsearch");
     *      }
     *      builder.endObject();
     *      IndexRequest indexRequest = new IndexRequest("posts")
     *      .id("1").source(builder);
     * 4. source:
     *      IndexRequest indexRequest = new IndexRequest("posts")
     *     .id("1")
     *     .source("user", "kimchy",
     *         "postDate", new Date(),
     *         "message", "trying out Elasticsearch");
     *
     *   报错：  Validation Failed: 1: type is missing;
     *      加入两个jar包解决
     *
     * @return
     */
    public IndexResponse add(String indexName, Object o) throws IOException {
        IndexRequest request = new IndexRequest(indexName);
        String userJson = JSON.toJSONString(o);
        request.source(userJson, XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return indexResponse;
    }

    private XContentBuilder generateBuilder() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                // es7及以后去掉了映射类型--person
                builder.startObject("name");
                {
                    builder.field("type", "text");
                    builder.field("analyzer", "ik_smart");
                }
                builder.endObject();
            }
            {
                builder.startObject("age");
                {
                    builder.field("type", "integer");
                }
                builder.endObject();
            }
            {
                builder.startObject("desc");
                {
                    builder.field("type", "text");
                    builder.field("analyzer", "ik_smart");
                }
                builder.endObject();
            }
            {
                builder.startObject("id");
                {
                    builder.field("type", "integer");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        /*.startObject().field("properties")
            .startObject().field("person")
                .startObject("name")
                    .field("type" , "text")
                    .field("analyzer", "ik_smart")
                .endObject()
                .startObject("age")
                    .field("type" , "int")
                .endObject()
                .startObject("desc")
                    .field("type", "text")
                    .field("analyzer", "ik_smart")
                .endObject()
            .endObject()
        .endObject();*/
        return builder;
    }



}
