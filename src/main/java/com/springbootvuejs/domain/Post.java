package com.springbootvuejs.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob // java String, DB에서는 LargeObject
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        // 서비스의 정책을 넣지마세요!!
        //return this.title.substring(0, 10);
        return this.title;
    }

/*    public void change(String content, String title) {
        this.title = title;
        this.content = content;
    }*/

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(PostEditor postEditor) {
        title = postEditor.getTitle();
        content = postEditor.getContent();
    }

    public void simpleEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
