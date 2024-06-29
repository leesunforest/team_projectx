package com.projectx.board.controller;
/*
localhost:8082 에서 map.html 에 접속하기 위한 임의의 컨트롤러
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String map() {
        return "map";
    }
}

/*
참고용 :
@Controller
public class MainController {

    @GetMapping("/")
    public String root (){
        return "redirect:/diary/list";

    }
}

기존 내용 :
    @GetMapping("/")
    public String map() {
        return "map";
    }

 */