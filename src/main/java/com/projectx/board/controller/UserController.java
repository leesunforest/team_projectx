package com.projectx.board.controller;

import com.projectx.board.dto.UserLoginDTO;
import com.projectx.board.dto.UserSignupDTO;
import com.projectx.board.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@ModelAttribute UserSignupDTO requestDTO, RedirectAttributes redirectAttributes) {
        try {
            userService.signup(requestDTO);
            redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다. 로그인 해주세요.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/signup";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "회원가입 중 오류가 발생했습니다.");
            return "redirect:/signup";
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDTO requestDTO, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            userService.login(requestDTO, request);
            redirectAttributes.addFlashAttribute("message", "로그인 성공");
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "로그인 중 오류가 발생했습니다.");
            return "redirect:/login";
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
