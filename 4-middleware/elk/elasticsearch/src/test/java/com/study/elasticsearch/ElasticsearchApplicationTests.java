package com.study.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ElasticsearchApplicationTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    void contextLoads() {
        System.out.println(restHighLevelClient);
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

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    class User{
        private String userName;
        private String gender;
        private Integer age;
    }


}
