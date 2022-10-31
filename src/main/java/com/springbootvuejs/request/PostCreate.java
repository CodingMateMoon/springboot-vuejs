package com.springbootvuejs.request;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Setter
@Getter
@ToString
//@AllArgsConstructor
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    public String title;

    @NotBlank(message = "컨텐트를 입력해주세요.")
    public String content;

    public PostCreate() {
    }

    // content, title 순서 잘못 입력시 문제가 발생할 수 있습니다. Builder 패턴 사용으로 이러한 문제를 예방합니다.
    /*
    빌더의 장점
    - 가독성이 좋습니다. (값 생성에 대해 유연합니다)
    - 필요한 값만 받을 수 있습니다. (오버로딩 가능한 조건 찾아보세요)
    - *객체의 불변성
     */
    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
