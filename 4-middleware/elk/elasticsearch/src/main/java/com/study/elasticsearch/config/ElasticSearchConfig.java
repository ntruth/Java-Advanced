package com.study.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ElasticSerachConfig
 * @Description TODO
 * @Author ann
 * @Date 2021/7/9 16:26
 * @Version 1.0
 */
/**
 * @author : ywb
 * @createdDate : 2020/5/31
 * @updatedDate
 */
@Configuration
public class ElasticSearchConfig {
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );
        return restHighLevelClient;
    }
}
