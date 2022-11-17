package com.springbootvuejs.repository;

import com.springbootvuejs.domain.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(int page);
}
