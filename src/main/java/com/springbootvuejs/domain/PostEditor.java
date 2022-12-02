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

    public static PostEditor.PostEditorBuilder builder() {
        return new PostEditor.PostEditorBuilder();
    }

    public static class PostEditorBuilder {
        private String title;
        private String content;

        PostEditorBuilder() {
        }

        public PostEditor.PostEditorBuilder title(final String title) {
            if (title != null)
                this.title = title;
            return this;
        }

        public PostEditor.PostEditorBuilder content(final String content) {
            if (content != null)
                this.content = content;
            return this;
        }

        public PostEditor build() {
            return new PostEditor(this.title, this.content);
        }

        public String toString() {
            return "PostEditor.PostEditorBuilder(title=" + this.title + ", content=" + this.content + ")";
        }
    }
}
