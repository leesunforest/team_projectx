package com.projectx.board.dto;

import lombok.Data;


@Data
public class FavoriteRequestDTO {
    // 저장 추가 요청 처리 클래스
    private String userId;
    private String storeName;
    private String storeAddress;
    private String storeNumber;

}