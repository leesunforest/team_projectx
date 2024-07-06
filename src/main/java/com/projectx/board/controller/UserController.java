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
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
            return "signup";
        }
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDTO userLoginDTO, HttpServletRequest request, Model model) {
        try {
            String userId = userService.login(userLoginDTO, request);
            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            return "redirect:/login?error=true"; // 로그인 페이지로 리디렉션하면서 URL 파라미터에 에러 메시지 추가
        }

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