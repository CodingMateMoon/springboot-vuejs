package com.springbootvuejs.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob // java String, DB에서는 LargeObject
    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}