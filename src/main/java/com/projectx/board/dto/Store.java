package com.projectx.board.dto;

import lombok.Data;

@Data
public class Store {
    // 외부 API로 가져온 가게 정보
    private String storeId; // 가게 아이디 
    private String storeName; // 가게 이름
    private String storeAddress; // 가게 주소
}
