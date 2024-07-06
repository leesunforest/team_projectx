package com.projectx.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

//    @GetMapping("/board/list")
//    public String boardList() {
//        return "boardList";
//    }

    @GetMapping("/mypage")
    public String mypage(){ return "mypage"; }
}
