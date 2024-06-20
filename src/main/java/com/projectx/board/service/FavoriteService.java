package com.projectx.board.service;

import com.projectx.board.dto.Store;
import com.projectx.board.entity.Favorite;
import com.projectx.board.entity.User;
import com.projectx.board.repository.FavoriteRepository;
import com.projectx.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    // 좋아요 저장
    public Favorite saveFavorite(Long userId, String storeId) {
        // 사용자 정보 가져오기
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // 외부 API를 통해 가게 정보 가져오기
        Store store = getStoreById(storeId).orElseThrow(() -> new RuntimeException("Store not found"));

        // 저장해야하는 Favorite을 빌더 패턴을 이용하여 생성
        Favorite favorite = Favorite.builder()
                .user(user)
                .storeId(storeId)
                .storeName(store.getStoreName())
                .build();

        // 생성한 Favorite 객체를 DB에 저장하고 반환
        return favoriteRepository.save(favorite);
    }

    // 좋아요 목록 조회
    public List<Favorite> getFavoritesByUser(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }

    // 좋아요 삭제
    public void deleteFavorite(Long favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }

    // 가게 정보 조회 (외부 API 사용)
    public Optional<Store> getStoreById(String storeId) {
        String apiUrl = "http://api.map-service.com/stores/" + storeId;

        // 외부 API 호출하여 Store 객체 반환
        return Optional.ofNullable(restTemplate.getForObject(apiUrl, Store.class));
    }
}
