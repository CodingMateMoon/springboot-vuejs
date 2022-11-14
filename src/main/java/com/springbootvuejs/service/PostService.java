package com.springbootvuejs.service;

import com.springbootvuejs.domain.Post;
import com.springbootvuejs.repository.PostRepository;
import com.springbootvuejs.request.PostCreate;
import com.springbootvuejs.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    // 글이 너무 많은 경우 비용이 많이 들 수 있습니다.
    // 수억건의 DB 글을 모두 조회하는 경우 DB가 죽을 수 있습니다.
    // DB -> 애플리케이션 서버로 전달하는 시간, 트래픽비용 등이 많이 발생할 수 있습니다.

    public List<PostResponse> getBoardList() {
        return postRepository.findAll().stream()
                .map(PostResponse::new
                )
                .collect(Collectors.toList());
    }

    public List<PostResponse> getBoardList(Pageable pageable) {
        // web -> page 1 요청이 왔을 때 내부적으로 0으로 부터 값을 넘겨서 데이터를 조회
//        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC,"id"));
        return postRepository.findAll(pageable).stream()
                .map(PostResponse::new
                )
                .collect(Collectors.toList());
    }

    /*
    Controller -> WebPostService(Response 서비스 호출 담당) -> Repository
                  PostService(외부 다른 서비스와 통신하는 서비스)
     */
}
