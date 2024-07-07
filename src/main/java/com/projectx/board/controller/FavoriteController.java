package com.projectx.board.controller;

import com.projectx.board.dto.FavoriteRequestDTO;
import com.projectx.board.dto.FavoriteResponseDTO;
import com.projectx.board.entity.Favorite;
import com.projectx.board.service.FavoriteService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Controller
@RequestMapping("/mypage/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    // userId 를 세션에서 받아 오는 경우 :
    @PostMapping("/save")
    public ResponseEntity<?> saveFavorite(@RequestBody FavoriteRequestDTO favoriteDTO, HttpSession httpSession) {
        Object userId = httpSession.getAttribute("userId");
        log.info("userId {} :", userId.toString());
        // 저장 로직
        Favorite favorite = favoriteService.saveFavorite(
                (String) userId,
                favoriteDTO.getStoreName(),
                favoriteDTO.getStoreAddress(),
                favoriteDTO.getStoreNumber());
        FavoriteResponseDTO responseDTO = new FavoriteResponseDTO(favorite);

        return ResponseEntity.ok(responseDTO);
    }

    // userId 세션을 받아오는 경우
    @GetMapping("/list")
    public String getFavorites(HttpSession session, Model model) {
        // 세션에서 userId 가져오기
        String userId = (String) session.getAttribute("userId");

        if(userId == null) {
            return "redirect:/login"; // login.html 로 이동
        }

        // userId로 목록 가져오기
        List<Favorite> favorites = favoriteService.getFavoritesByUserId(userId);
        model.addAttribute("favorites", favorites);
        model.addAttribute("userId", userId);

        return "favoriteList"; // favoriteList.html 이동
    }

    // 저장한 정보 삭제 요청 메서드
    @DeleteMapping("/delete/{favoriteId}")
    public ResponseEntity<?> deleteFavorite(@PathVariable Long favoriteId) {
        try {
            // 삭제 로직
            favoriteService.deleteFavorite(favoriteId);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제에 실패했습니다: " + e.getMessage());
        }
    }

    // 예외 처리 메서드
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
    }
}