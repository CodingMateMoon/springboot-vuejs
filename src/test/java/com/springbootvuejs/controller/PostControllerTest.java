package com.springbootvuejs.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

        //expected
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다.\", \"content\": \"내용입니다.\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청 시 title값은 필수입니다.")
    void postTest() throws Exception {  // 가능하면 application/json을 권장합니다. (기존 application/x-www-form-urlencoded)

        //expected
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": null, \"content\": \"내용입니다.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("타이틀을 입력해주세요"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("400 Error 잘못된 요청입니다.")
    void badRequestTest() throws Exception {  // 가능하면 application/json을 권장합니다. (기존 application/x-www-form-urlencoded)

        //expected
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": null, \"content\": \"내용입니다.\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andDo(MockMvcResultHandlers.print());
    }
}