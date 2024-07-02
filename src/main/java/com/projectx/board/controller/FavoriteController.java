package com.projectx.board.controller;

import com.projectx.board.entity.Favorite;
import com.projectx.board.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    // 저장 요청 처리 메서드 : 저장하기 위한 정보를 받아옴
    @PostMapping("/save")
    public Favorite saveFavorite(@RequestParam String userId, @RequestParam String storeName, @RequestParam String storeAddress, @RequestParam String storeNumber) {
        return favoriteService.saveFavorite(userId, storeName, storeAddress, storeNumber);
    }

    // 특정 사용자의 저장 목록 조회 요청 메서드
    @GetMapping("/{userNo}")
    public List<Favorite> getFavorites(@PathVariable Long userNo) {
        // userNo 를 이용하여 목록을 조회함
        return favoriteService.getFavorites(userNo);
    }

    // 저장한 정보 상세 보기 요청 메서드
    @GetMapping("/details/{favoriteId}")
    public Favorite getFavoriteDetails(@PathVariable Long favoriteId) {
        // favoriteId 를 이용하여 상세보기
        return favoriteService.getFavoriteDetails(favoriteId);
    }

    // 저장한 정보 삭제 요청 메서드
    @DeleteMapping("/delete/{favoriteId}")
    public void deleteFavorite(@PathVariable Long favoriteId) {
        // favoriteId 를 이용하여 삭제
        favoriteService.deleteFavorite(favoriteId);
    }
}
