package com.projectx.board.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        model.addAttribute("userId", userId);
        return "home";
    }

    @GetMapping("/boardList")
    public String boardList() {
        return "boardList";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
}
