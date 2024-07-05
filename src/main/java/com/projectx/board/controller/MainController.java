package com.projectx.board.controller;
/*
localhost:8082 에서 map.html 에 접속하기 위한 임의의 컨트롤러
 */

import com.projectx.board.entity.User;
import com.projectx.board.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;
    @RequestMapping("/")
    public String mapPage(Model model, HttpSession session) {
        // userId 의 세션값을 가지고 옴
        String userId = (String) session.getAttribute("userId");

        // UserEntity 조회
        Optional<User> user = userRepository.findByUserId(userId);

        // UserEntity가 존재하면 userId 설정, 없으면 defaultUser 설정
        String displayUserId = user.map(User::getUserId).orElse("defaultUser");

        // 모델에 userId 추가
        model.addAttribute("userId", displayUserId);

        return "map";
    }

    @GetMapping("/mypage/favorites")
    public String favoriteListPage() {
        return "favoriteList"; // templates 디렉토리의 favoriteList.html을 반환
    }
}
