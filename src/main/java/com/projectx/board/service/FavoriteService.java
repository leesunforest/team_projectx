package com.projectx.board.service;

import com.projectx.board.entity.Favorite;
import com.projectx.board.entity.UserEntity;
import com.projectx.board.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserService userService;

    // 가게 정보를 저장하는 메서드
    public Favorite saveFavorite(String userId, String storeName, String storeAddress, String storeNumber) {

        // 사용자 아이디로 사용자 정보 조회
        UserEntity user = userService.findUserByUserId(userId);

        if (user == null) {
            throw new IllegalArgumentException("유저 정보 에러");
        }

        // favorite 에 저장해야 하는 값 지정
        Favorite favorite = Favorite.builder()
                .storeName(storeName)
                .storeAddress(storeAddress)
                .storeNumber(storeNumber)
                .user(user)
                .userId(user.getUserId())
                .build();

        // 가게 정보를 저장
        return favoriteRepository.save(favorite);
    }

    // 사용자의 저장 목록 조회 메서드
    public List<Favorite> getFavorites(Long userNo) {
        // userNo 로 사용자 구별
        return favoriteRepository.findByUserUserNo(userNo);
    }

    // 저장한 정보 상세 보기 메서드
    public Favorite getFavoriteDetails(Long favoriteId) {
        // favoriteId 를 이용하여 상세보기
        return favoriteRepository.findById(favoriteId).orElseThrow(() -> new IllegalArgumentException("Favorite not found"));
    }

    // 저장한 정보 삭제 메서드
    public void deleteFavorite(Long favoriteId) {
        // favoriteId 를 이용하여 삭제
        favoriteRepository.deleteById(favoriteId);
    }
}