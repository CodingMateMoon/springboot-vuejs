package com.springbootvuejs.service;

import com.springbootvuejs.domain.Post;
import com.springbootvuejs.repository.PostRepository;
import com.springbootvuejs.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    // @Autowired - field injection은 테스트 용도외에는 가급적 사용X. 생성자 injection 권장

    private final PostRepository postRepository;

    public Long write(PostCreate postCreate) {
        // postCreate -> Entity
//        Post post = new Post(postCreate.getTitle(), postCreate.getContent());
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);

        return post.getId();
    }
}
