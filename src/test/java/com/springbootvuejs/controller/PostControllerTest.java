package com.springbootvuejs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootvuejs.config.DatabaseCleanup;
import com.springbootvuejs.domain.Post;
import com.springbootvuejs.repository.PostRepository;
import com.springbootvuejs.request.PostCreate;
import com.springbootvuejs.request.PostEdit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest
@AutoConfigureMockMvc // @WebMvcTest을 구성하는 Annotation 중 하나
@SpringBootTest // 웹 전반적인 테스트
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    void clean() {
//        postRepository.deleteAll();
        databaseCleanup.execute();
    }

    @Test
    @DisplayName("/posts 요청시 Hello World를 출력한다.")
    void indexTest() throws Exception {  // 가능하면 application/json을 권장합니다. (기존 application/x-www-form-urlencoded)
        //  글 제목
        // 글 내용
        // 사용자
            // id
            // name
            // level
        /** json 형식으로 보낼 경우 user 키의 value에 대해 key value를 가진 object를 넣는 등 계층 구조의 데이터를 보낼 때 효과적입니다. 반면 기존의 key=value를 &로 이어서 보내는 application/x-www-form-urlencoded 형식은 데이터를 표현하는 데에 한계가 있습니다.
         * {
         *  "title": "xx",
         *  "content": "xx",
         *  "user": {
         *          "id":"xx",
         *          "name": "xx",
         *          "level": "xx"
         *      }
         * }
         */
        
        //given
//        PostCreate request = new PostCreate("제목입니다.", "내용입니다.");

        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();


        String json = objectMapper.writeValueAsString(request);

        System.out.println(json);

        //expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
//                .andExpect(content().string("{}"))
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("400 Error 잘못된 요청입니다. /posts 요청 시 title값은 필수입니다.")
    void requestWithoutTitleTest() throws Exception {  // 가능하면 application/json을 권장합니다. (기존 application/x-www-form-urlencoded)
        PostCreate request = PostCreate.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }


    @Test
    @DisplayName("/posts 요청시 DB에 값을 저장합니다.")
    void savePostTest() throws Exception {  // 가능하면 application/json을 권장합니다. (기존 application/x-www-form-urlencoded)
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1L,postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void getBoard() throws Exception {
        // given
        Post post = Post.builder()
                .title("123456789012345")
//                .title("12345")
                .content("content")
                .build();
        postRepository.save(post);

        //expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
//                .andExpect(jsonPath("$.title").value("12345"))
                .andExpect(jsonPath("$.content").value("content"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void getBoardList() throws Exception {
        // given
        Post post1 = postRepository.save(Post.builder()
                .title("title1")
                .content("content1")
                .build());

        Post post2 = postRepository.save(Post.builder()
                .title("title2")
                .content("content2")
                .build());

        //expected
        mockMvc.perform(get("/list")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                /*
                 {id:..., title: ...}
                 */
                /*
                  {id: ..., title: ...}, {id: ..., title: ...}
                 */
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].id").value(post1.getId()))
                .andExpect(jsonPath("$[0].title").value("title1"))
                .andExpect(jsonPath("$[0].content").value("content1"))
                .andExpect(jsonPath("$[1].id").value(post2.getId()))
                .andExpect(jsonPath("$[1].title").value("title2"))
                .andExpect(jsonPath("$[1].content").value("content2"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 여러개 조회2")
    void getBoardList2() throws Exception {
        postRepository.deleteAll();
        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("codingmate 제목 " + i)
                        .content("구글 " + i)
                        .build()
                )
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        //expected
//        mockMvc.perform(get("/board?page=1&sort=id,desc&size=5")
        // 쿼리 파라미터로 title 정렬 요청했을 때 index가 안 걸려있는 경우 속도가 느려질 수 있습니다.
        mockMvc.perform(get("/list?page=1&size=10&sort=id,desc")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].id").value(30))
                .andExpect(jsonPath("$[0].title").value("codingmate 제목 30"))
                .andExpect(jsonPath("$[0].content").value("구글 30"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져옵니다")
    void getBoardList3() throws Exception {
        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("codingmate 제목 " + i)
                        .content("구글 " + i)
                        .build()
                )
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        //expected
//        mockMvc.perform(get("/board?page=1&sort=id,desc&size=5")
        // 쿼리 파라미터로 title 정렬 요청했을 때 index가 안 걸려있는 경우 속도가 느려질 수 있습니다.
        mockMvc.perform(get("/board?page=0&size=10")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].title").value("codingmate 제목 9"))
                .andExpect(jsonPath("$[0].content").value("구글 9"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 제목 수정")
    void updateBoardTitle() throws Exception {
        //given
        Post post = Post.builder()
                .title("codingmatemoon")
                .content("구글")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("codingmatemoon")
                .content("IBM")
                .build();


        mockMvc.perform(patch("/posts/{postId}", post.getId())  // patch / posts/postId
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteBoard() throws Exception {
        //given
        Post post = Post.builder()
                .title("codingmatemoon")
                .content("구글")
                .build();
        postRepository.save(post);
        //expected
        mockMvc.perform(delete("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    void getBoardNotExists() throws Exception {

        //expected
        mockMvc.perform(delete("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 수정")
    void updateBoardNotExists() throws Exception {
        //given
        PostEdit postEdit = PostEdit.builder()
                .title("codingmatemoon")
                .content("구글")
                .build();

        //expected
        mockMvc.perform(patch("/posts/{postId}", 1L)  // patch / posts/postId
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}