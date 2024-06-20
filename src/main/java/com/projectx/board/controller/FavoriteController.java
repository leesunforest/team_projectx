package com.projectx.board.controller;

import com.projectx.board.dto.Store;
import com.projectx.board.entity.Favorite;
import com.projectx.board.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    // 좋아요 저장
    @PostMapping("/save")
    public Favorite saveFavorite(@RequestParam Long userId, @RequestParam String storeId) {
        return favoriteService.saveFavorite(userId, storeId);
    }

    // 목록 가져오기
    @GetMapping("/list")
    public List<Favorite> getFavoritesByUser(@RequestParam Long userId) {
        return favoriteService.getFavoritesByUser(userId);
    }

    // 저장한 가게
    @GetMapping("/favorite/store/{storeId}")
    public Optional<Store> getStoreById(@PathVariable String storeId) {
        return favoriteService.getStoreById(storeId);
    }

    // 좋아요 가게 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteFavorite(@PathVariable Long favoriteId) {
        favoriteService.deleteFavorite(favoriteId);
    }
}
