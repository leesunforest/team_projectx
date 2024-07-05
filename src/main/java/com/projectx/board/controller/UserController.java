package com.projectx.board.controller;

import com.projectx.board.dto.UserLoginDTO;
import com.projectx.board.dto.UserSignupDTO;
import com.projectx.board.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {


    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@ModelAttribute UserSignupDTO requestDTO, Model model) {
        try {
            userService.signup(requestDTO);
            model.addAttribute("message", "회원가입 성공");
            return "redirect:/login?message=회원가입 성공";
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
            return "signup";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDTO userLoginDTO, HttpServletRequest request, Model model) {
        String userId = userService.login(userLoginDTO, request);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
