package com.springbootvuejs.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PostSearch {
    private int page;
    private int size;

    @Builder
    public PostSearch(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
