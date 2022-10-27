package com.springbootvuejs.controller;

import com.springbootvuejs.request.PostCreate;
import com.springbootvuejs.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// RestController를 활용하면 @Controller, @ResponseBody 등 Controller보다 좀 더 편하게 web 관련처리를 할 수 있습니다.
@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {


    private final PostService postService;

    // Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글 등록
    // POST Method

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate request) throws Exception {
        // 데이터를 검증하는 이유
        // 1. client 개발자가 깜박할 수 있습니다. 실수로 값을 안보낼 수 있습니다.
        // 2. client bug로 값이 누락될 수 있습니다.
        // 3. 외부에 나쁜 사람이 값을 임의로 조작해서 보낼 수 있습니다.
        // 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있습니다.
        // 5. 서버 개발자의 편안함을 위해서 (검증이 되었기 때문에 안도)

        /*
        1. 매번 메서드마다 값을 검증해야 한다
         > 개발자가 까먹을 수 있습니다
         > 검증 부분에서 버그가 발생할 여지가 높습니다
         > 지겹습니다. (간지X)

        2. 응답값에 HashMap-> 응답 클래스를 만들어주는게 좋습니다
        3. 여러 개의 에러 처리가 힘듭니다.
        4. 세 번 이상의 반복적인 작업은 피해야합니다. (자동화 고려)
         ** 코드 && 개발에 관한 모든것


        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);
            String fieldName = firstFieldError.getField();
            String errorMessage = firstFieldError.getDefaultMessage();

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }

         */
        postService.write(request);
        return Map.of();

        //log.info("params{}", params.toString());


        /*
        String title = params.getTitle();
        if (title == null || "".equals(title)) {
            // {"title": ""}
            // {"title": "              "}
            // 필드 수가 많을수록 노가다. 누락 가능성
            // 개발팁 - 무언가 3번이상 반복작업을 할 때 뭔가 잘못하고 있는건 아닐지 의심해야 합니다.
            // 검증해야할 게 많습니다. (꼼꼼하지 않을 수 있습니다.)

            throw new Exception("title값이 없습니다");
        }
        String content = params.getContent();
        return "Hello World";
         */
    }
}
