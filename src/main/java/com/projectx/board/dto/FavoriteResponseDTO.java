package com.projectx.board.dto;

import com.projectx.board.entity.Favorite;
import lombok.Getter;

@Getter
public class FavoriteResponseDTO {
    // Favorite Entity를 JSON으로 반환하기 위한 DTO
    private Long favoriteId;
    private String storeName;
    private String storeAddress;
    private String storeNumber;
    private String userId;

    public FavoriteResponseDTO(Favorite favorite) {
        this.favoriteId = favorite.getFavoriteId();
        this.storeName = favorite.getStoreName();
        this.storeAddress = favorite.getStoreAddress();
        this.storeNumber = favorite.getStoreNumber();
        this.userId = favorite.getUserId();
    }
}
