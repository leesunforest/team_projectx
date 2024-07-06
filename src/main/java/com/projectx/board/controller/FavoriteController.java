package com.projectx.board.controller;

import com.projectx.board.dto.FavoriteRequestDTO;
import com.projectx.board.dto.FavoriteResponseDTO;
import com.projectx.board.entity.Favorite;
import com.projectx.board.service.FavoriteService;
import jakarta.servlet.http.HttpServletRequest;
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


    // 특정 사용자의 저장 목록 조회 요청 메서드
    /*
    @GetMapping("/list/{userNo}")
    public String getFavorites(@PathVariable Long userNo, Model model) {
        List<Favorite> favorites = favoriteService.getFavorites(userNo);
        model.addAttribute("favorites", favorites);

        return "favoriteList"; // favoriteList.Html 로 가기 위함
    }
     */

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

    // 저장한 정보 상세 보기 요청 메서드
    @GetMapping("/details/{favoriteId}")
    public FavoriteResponseDTO getFavoriteDetails(@PathVariable Long favoriteId) {
        Favorite favorite = favoriteService.getFavoriteDetails(favoriteId);

        return new FavoriteResponseDTO(favorite);
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

    /*
    // userId 세션을 가져오는 경우
    @DeleteMapping("/delete/{favoriteId}")
    public ResponseEntity<?> deleteFavorite(@PathVariable Long favoriteId, HttpSession session) {
        try {
            // 세션에서 userId 가져오기
            String userId = (String) session.getAttribute("userId");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
            }

            // 삭제 로직
            favoriteService.deleteFavorite(favoriteId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the favorite: " + e.getMessage());
        }
    }
     */

    // 예외 처리 메서드
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
    }
}