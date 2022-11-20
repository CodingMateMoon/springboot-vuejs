package com.springbootvuejs.repository;

import com.springbootvuejs.domain.Post;
import com.springbootvuejs.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
