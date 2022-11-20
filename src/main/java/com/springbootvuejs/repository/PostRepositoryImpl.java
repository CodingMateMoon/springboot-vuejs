package com.springbootvuejs.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springbootvuejs.domain.Post;
import com.springbootvuejs.domain.QPost;
import com.springbootvuejs.request.PostSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl  implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(QPost.post) //  select from 같이 적용. 컴파일하면서 생성된 QPost 활용
                .limit(postSearch.getSize())
                .offset((long) (postSearch.getPage() - 1) * postSearch.getSize())
                .orderBy(QPost.post.id.desc())
                .fetch();
    }
}
