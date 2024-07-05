package com.projectx.board.controller;
/*
localhost:8082 에서 map.html 에 접속하기 위한 임의의 컨트롤러
 */

import com.projectx.board.repository.UserRepository;
import com.projectx.board.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class MainController {

    @GetMapping("/map")
    public String mapPage(HttpSession session, Model model) {
        // 세션에서 userId 가져오기
        String userId = (String) session.getAttribute("userId");

        // userId 를 모델에 담아 view에 전달
        model.addAttribute("userId", userId);
        System.out.println(userId);
        return "map";
    }

    @GetMapping("/mypage/favorites")
    public String favoriteListPage(HttpSession session, Model model) {
        // 세션에서 userId 가져오기
        String userId = (String) session.getAttribute("userId");
        
        model.addAttribute("userId", userId);

        return "favoriteList";
    }

/*  MainController 원형

    @RequestMapping("/map")
    public String mapPage() {
        return "map"; // map.html 으로 이동
    }

    @GetMapping("/mypage/favorites")
    public String favoriteListPage() {
        return "favoriteList"; // templates 디렉토리의 favoriteList.html을 반환
    }

*/
}
