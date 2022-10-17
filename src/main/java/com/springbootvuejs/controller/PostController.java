package com.springbootvuejs.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// RestController를 활용하면 @Controller, @ResponseBody 등 Controller보다 좀 더 편하게 web 관련처리를 할 수 있습니다.
@Slf4j
@RestController
public class PostController {

    // Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글 등록
    // POST Method

    //@GetMapping("/posts")
    @PostMapping("/posts")
//    @RequestMapping(method = RequestMethod.GET, path ="/v1/posts")
    public String post(@RequestParam String title, @RequestParam String content){
        log.info("title={}, content={}", title, content);
        return "Hello World";
    }
}
