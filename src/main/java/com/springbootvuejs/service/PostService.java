package com.springbootvuejs.service;

import com.springbootvuejs.domain.Post;
import com.springbootvuejs.repository.PostRepository;
import com.springbootvuejs.request.PostCreate;
import com.springbootvuejs.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    // @Autowired - field injection은 테스트 용도외에는 가급적 사용X. 생성자 injection 권장

    private final PostRepository postRepository;

    //public Long write(PostCreate postCreate) {
    public void write(PostCreate postCreate) {
        // postCreate -> Entity
//        Post post = new Post(postCreate.getTitle(), postCreate.getContent());
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
//        return post.getId();
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .title((post.getTitle()))
                .content(post.getContent())
                .build();

        return postResponse;
    }

    public List<Post> getBoardList() {
        return postRepository.findAll();
    }

    /*
    Controller -> WebPostService(Response 서비스 호출 담당) -> Repository
                  PostService(외부 다른 서비스와 통신하는 서비스)
     */
}
