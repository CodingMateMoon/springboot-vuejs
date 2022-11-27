package com.springbootvuejs.request;


import lombok.*;

import javax.validation.constraints.NotBlank;

// 기능이 다를 경우 별도 클래스로 분리해야합니다.
@Setter
@Getter
@ToString
@NoArgsConstructor
public class PostEdit {

    @NotBlank(message = "타이틀을 입력해주세요.")
    public String title;

    @NotBlank(message = "컨텐트를 입력해주세요.")
    public String content;

    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
