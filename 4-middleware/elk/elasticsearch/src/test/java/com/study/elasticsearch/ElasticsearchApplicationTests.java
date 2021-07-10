package com.study.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.study.elasticsearch.model.Member;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootTest
class ElasticsearchApplicationTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    void contextLoads() {
        System.out.println(restHighLevelClient);
    }

    //创建索引
    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("user");
        CreateIndexResponse response = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * 测试索引是否存在
     *
     * @throws IOException
     */
    @Test
    public void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("user");
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 删除索引
     */
    @Test
    public void deleteIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("user");
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

    /**
     * 测试添加文档
     *
     * @throws IOException
     */
    @Test
    public void createDocument() throws IOException {
        Member user = new Member("user", 18);
        IndexRequest request = new IndexRequest("user");
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
        //将我们的数据放入请求，json
        request.source(JSON.toJSONString(user), XContentType.JSON);
        //客服端发送请求
        IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(index.toString());
        //对应我们的命令返回状态
        System.out.println(index.status());
    }

    //判断是否存在文档
    @Test
    public void testIsExist() throws IOException {
        GetRequest getRequest = new GetRequest("user", "1");
        //不获取返回的source的上下文
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //获取文档信息
    @Test
    public void testGetDocument() throws IOException {
        GetRequest getRequest = new GetRequest("user", "1");
        GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        //打印文档信息
        System.out.println(response.getSourceAsString());
        System.out.println(response);
    }

    //更新文档信息
    @Test
    public void testUpdateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest("ywb", "1");
        request.timeout("1s");
        Member user = new Member("ywb java", 19);
        request.doc(JSON.toJSONString(user), XContentType.JSON);
        UpdateResponse update = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(update);
        System.out.println(update.status());
    }

    //删除文档
    @Test
    public void testDeleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("user", "1");
        request.timeout("10s");
        Member user = new Member("ywb java", 19);
        DeleteResponse update = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(update.status());
    }

    //批量插入数据
    @Test
    public void testBulkRequest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        ArrayList<Member> users = new ArrayList<>();
        users.add(new Member("zhangsan", 1));
        users.add(new Member("lishi", 12));
        users.add(new Member("wangwu", 13));
        users.add(new Member("zhaoliu", 14));
        users.add(new Member("tianqi", 15));
        for (int i = 0; i < users.size(); i++) {
            bulkRequest.add(
                    new IndexRequest("user")
                            .id("" + i + 1)
                            .source(JSON.toJSONString(users.get(i)), XContentType.JSON)
            );
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk);
    }




    //向对应的index中写入数据
    @Test
    public void indexData() throws IOException {
        //初始化请求，构造函数中指定index名
        IndexRequest indexRequest = new IndexRequest("users");
        //设置id
        indexRequest.id("2");
       indexRequest.source("userName", "zhangsan");
        indexRequest.source("age", 18);
//        User user = new User("zhangsan", "male", 18);
//        String jsonString = JSONObject.(user);
        //要保存的内容
//        indexRequest.source(jsonString, XContentType.JSON);
        //执行操作
        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        //提取有用的响应数据
        System.out.println(index);
    }




}
