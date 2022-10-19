package com.springbootvuejs.controller;

import com.springbootvuejs.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

// RestController를 활용하면 @Controller, @ResponseBody 등 Controller보다 좀 더 편하게 web 관련처리를 할 수 있습니다.
@Slf4j
@RestController
public class PostController {

    // Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글 등록
    // POST Method

    @PostMapping("/posts")
    public String post(@RequestBody PostCreate params) throws Exception {
        // 데이터를 검증하는 이유
        // 1. client 개발자가 깜박할 수 있습니다. 실수로 값을 안보낼 수 있습니다.
        // 2. client bug로 값이 누락될 수 있습니다.
        // 3. 외부에 나쁜 사람이 값을 임의로 조작해서 보낼 수 있습니다.
        // 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있습니다.
        // 5. 서버 개발자의 편안함을 위해서 (검증이 되었기 때문에 안도)

        log.info("params{}", params.toString());

        String title = params.getTitle();
        /*
        if (title == null || "".equals(title)) {
            // {"title": ""}
            // {"title": "              "}
            // 필드 수가 많을수록 노가다. 누락 가능성
            // 개발팁 - 무언가 3번이상 반복작업을 할 때 뭔가 잘못하고 있는건 아닐지 의심해야 합니다.
            // 검증해야할 게 많습니다. (꼼꼼하지 않을 수 있습니다.)

            throw new Exception("title값이 없습니다");
        }
         */

        String content = params.getContent();

        return "Hello World";
    }
}
