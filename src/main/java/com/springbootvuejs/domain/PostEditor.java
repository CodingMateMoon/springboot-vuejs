package com.springbootvuejs.domain;

import lombok.Builder;
import lombok.Getter;

// 수정할 수 있는 필드들에 대해 정의
@Getter
public class PostEditor {

    private final String title;
    private final String content;

    @Builder
    public PostEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
