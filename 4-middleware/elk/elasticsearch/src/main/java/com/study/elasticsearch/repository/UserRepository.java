package com.study.elasticsearch.repository;

import com.study.elasticsearch.model.Member;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public class UserRepository  implements ElasticsearchRepository<Member, 1> {
}
