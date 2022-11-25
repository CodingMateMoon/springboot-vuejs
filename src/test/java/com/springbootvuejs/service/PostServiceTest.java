package com.springbootvuejs.service;

import com.springbootvuejs.domain.Post;
import com.springbootvuejs.repository.PostRepository;
import com.springbootvuejs.request.PostCreate;
import com.springbootvuejs.request.PostEdit;
import com.springbootvuejs.request.PostSearch;
import com.springbootvuejs.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void saveBoard() {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //when
        postService.write(postCreate);

        // then
        Assertions.assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void getBoard() {
        // given
        Post requestPost = Post.builder()
                .title("title1234567890")
                .content("content")
                .build();
        postRepository.save(requestPost);
        // when
        PostResponse postResponse = postService.get(requestPost.getId());

        // then
        assertNotNull(postResponse);
        assertEquals(1L, postRepository.count());
        assertEquals("title12345", postResponse.getTitle());
        assertEquals("content", postResponse.getContent());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void getBoardList() {
        // given
        Post requestPost1 = Post.builder()
                .title("title1")
                .content("content1")
                .build();
        postRepository.save(requestPost1);

        Post requestPost2 = Post.builder()
                .title("title2")
                .content("content2")
                .build();
        postRepository.save(requestPost2);
        // when
        List<PostResponse> posts = postService.getBoardList();

        //then
        assertEquals(2L, posts.size());
    }

    @Test
    @DisplayName("글 여러개 저장")
    void saveBoardList() {
        // given
        postRepository.saveAll(List.of(
                Post.builder()
                        .title("title1")
                        .content("content1")
                        .build(),
                Post.builder()
                        .title("title2")
                        .content("content2")
                        .build()
        ));

        // when
        List<PostResponse> posts = postService.getBoardList();

        //then
        assertEquals(2L, posts.size());
    }

    /*@Test
    @DisplayName("글 1페이지 조회")
    void getBoardPage_backup() {
        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                            .title("codingmate 제목 " + i)
                            .content("구글 " + i)
                            .build()
                )
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");

        // when
        List<PostResponse> posts = postService.getBoardList(pageable);
        //then
        assertEquals(10L, posts.size());
        assertEquals("codingmate 제목 19", posts.get(0).getTitle());
//        assertEquals("codingmate 제목 26", posts.get(4).getTitle());
    }*/

    @Test
    @DisplayName("글 1페이지 조회")
    void getBoardPageWithPostSearch() {
        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("codingmate 제목 " + i)
                        .content("구글 " + i)
                        .build()
                )
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
//                .size(10)
                .build();
        // when
        List<PostResponse> posts = postService.getBoardList(postSearch);
        //then
        assertEquals(10L, posts.size());
        assertEquals("codingmate 제목 9", posts.get(0).getTitle());
//        assertEquals("codingmate 제목 26", posts.get(4).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void updateBoardTitle() {
        //given
        Post post = Post.builder()
                .title("codingmatemoon")
                .content("구글")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("codingmatesun")
                .build();

        // when
        postService.edit(post.getId(),postEdit);

        //then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));
        assertEquals("codingmatesun", changedPost.getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void updateBoardTitleTest() {
        //given
        Post post = Post.builder()
                .title("codingmatemoon")
                .content("구글")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("codingmatesun")
                .build();

        // when
        postService.edit(post.getId(),postEdit);

        //then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));
        assertEquals("codingmatesun", changedPost.getTitle());
        assertEquals("구글", changedPost.getContent());
    }

}