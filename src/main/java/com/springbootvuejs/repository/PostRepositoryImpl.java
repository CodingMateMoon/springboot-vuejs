package com.springbootvuejs.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springbootvuejs.domain.Post;
import com.springbootvuejs.domain.QPost;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl  implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(int page) {
        return jpaQueryFactory.selectFrom(QPost.post) //  select from 같이 적용. 컴파일하면서 생성된 QPost 활용
                .limit(10)
                .offset((long)(page - 1) * 10)  // 10개만 가져오는 페이징 처리 쿼리 수행
                .fetch(); //
    }
}
