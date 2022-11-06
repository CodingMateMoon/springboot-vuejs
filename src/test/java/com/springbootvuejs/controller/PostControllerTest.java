package com.springbootvuejs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootvuejs.domain.Post;
import com.springbootvuejs.repository.PostRepository;
import com.springbootvuejs.request.PostCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
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

}