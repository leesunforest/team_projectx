package com.projectx.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity @Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;

    @ManyToOne // N:1 관계
    @JoinColumn(name = "user_id", nullable = false) // user_id 컬럼이 Favorite 테이블에 생성되어 User 테이블의 기본 키를 참조하게 함
    private User user;

    private String storeId; // 가게 ID
    private String storeName; // 가게 이름

    private LocalDateTime createdAt; // 저장한 날짜와 시간

    @PrePersist // 엔티티가 저장되기 전 호출되는 메서드
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // 현재 날짜와 시간
    }

}
