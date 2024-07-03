package com.projectx.board.controller;

import com.projectx.board.dto.FavoriteRequestDTO;
import com.projectx.board.dto.FavoriteResponseDTO;
import com.projectx.board.entity.Favorite;
import com.projectx.board.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    // 저장 요청 처리 메서드 : 저장하기 위한 정보를 받아옴
    @PostMapping("/save")
    public ResponseEntity<?> saveFavorite(@RequestBody FavoriteRequestDTO favoriteDTO) {
        try {
            // 저장 로직
            Favorite favorite = favoriteService.saveFavorite(
                    favoriteDTO.getUserId(),
                    favoriteDTO.getStoreName(),
                    favoriteDTO.getStoreAddress(),
                    favoriteDTO.getStoreNumber());

            FavoriteResponseDTO responseDTO = new FavoriteResponseDTO(favorite);

            return ResponseEntity.ok(responseDTO);
        } catch(Exception e) {
            // 예외 처리
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving the favorite: " + e.getMessage());
        }
    }

    // 특정 사용자의 저장 목록 조회 요청 메서드
    @GetMapping("/list/{userNo}")
    public List<FavoriteResponseDTO> getFavorites(@PathVariable Long userNo) {
        List<Favorite> favorites = favoriteService.getFavorites(userNo);

        return favorites.stream().map(FavoriteResponseDTO::new).collect(Collectors.toList());
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
            // 예외처리
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the favorite: " + e.getMessage());
        }
    }

    // 예외 처리 메서드
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
    }
}