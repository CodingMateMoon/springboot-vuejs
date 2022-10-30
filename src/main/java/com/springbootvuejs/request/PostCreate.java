package com.springbootvuejs.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
//@AllArgsConstructor
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    public String title;

    @NotBlank(message = "컨텐트를 입력해주세요.")
    public String content;

    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
