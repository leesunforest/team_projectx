package com.projectx.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity @Getter @Table(name = "Favorite")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id", updatable = false)
    private Long favoriteId; // 저장 고유 번호

    @Column(name = "storeName", nullable = false)
    private String storeName; // 가게 이름

    @Column(name = "storeAddress", nullable = false)
    private String storeAddress; // 가게 주소

    @Column(name = "storeNumber")
    private String storeNumber; // 가게 전화번호

    @Column(name = "favorite_at", nullable = false)
    private LocalDateTime favoriteAt; // 저장 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    @JsonIgnore
    private User user; // 사용자 고유 번호

    @Column(name = "user_id", nullable = false, length = 15)
    private String userId; // 사용자 ID

    @Builder
    public Favorite(String storeName, String storeAddress, String storeNumber, User user, String userId) {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storeNumber = storeNumber;
        this.user = user;
        this.userId = userId;
        this.favoriteAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        this.favoriteAt = LocalDateTime.now();
    }
}