package com.springbootvuejs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// RestController를 활용하면 @Controller, @ResponseBody 등 Controller보다 좀 더 편하게 web 관련처리를 할 수 있습니다.
@RestController
public class PostController {

    @GetMapping("/posts")
//    @RequestMapping(method = RequestMethod.GET, path ="/v1/posts")
    public String get() {
        return "Hello World";
    }
}
