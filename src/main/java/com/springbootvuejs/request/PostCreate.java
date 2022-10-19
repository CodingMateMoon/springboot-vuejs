package com.springbootvuejs.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
public class PostCreate {

    @NotBlank // 빈값이 들어오면 오류를 발생시킵니다
    public String title;

    @NotBlank
    public String content;

    /*@Override
    public String toString() {
        return "PostCreate{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }*/
}
