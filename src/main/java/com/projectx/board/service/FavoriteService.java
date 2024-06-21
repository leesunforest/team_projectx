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

    // 실제로 외부 API 값을 못 받아오기 때문에 설정한 변수들. 이후 외부 API 연결을 하면 @Autowired 를 이용해서 주입
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final RestTemplate restTemplate; // config 패키지에서 임의의 AppConfig 를 패키지를 만들어서 Bean 생성

    @Autowired
    // 외부 API를 실제로 못 받아 오기 때문에 임의로 이용하기 위한 생성자
    public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository, RestTemplate restTemplate) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    // 좋아요 저장
    public Favorite saveFavorite(Long userId, String storeId) {
        // 사용자 정보 가져오기
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // 외부 API를 통한 가게 정보 가져오기(임의)
        Store store = getStoreById(storeId).orElseThrow(() -> new RuntimeException("Store not found"));

        // 저장해야 하는 Favorite을 빌더 패턴을 이용하여 생성 및 DB에 저장 후 반환
        Favorite favorite = Favorite.builder()
                .user(user)
                .storeId(storeId)
                .storeName(store.getStoreName())
                .build();

        // Favorite 객체를 DB 저장
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

    // 가게 정보 조회 (외부 API 사용, 현재 구현된 API가 없기 때문에 임의)
    public Optional<Store> getStoreById(String storeId) {
        String apiUrl = "http://api.example-service.com/stores/" + storeId;

        // 외부 API 호출하여 Store 객체 반환
        return Optional.ofNullable(restTemplate.getForObject(apiUrl, Store.class));
    }

}
