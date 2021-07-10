package com.study.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
    private String userName;
    private String gender;
    private Integer age;

    public Member(String user, int i) {
        this.userName = user;
        this.age = i;
    }
}