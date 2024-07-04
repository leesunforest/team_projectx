package com.projectx.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class UserEntity {
    @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long userNo;

    @Column(name = "user_id", length = 15, nullable = false)
    private String userId;

    @Column(name = "user_pw", length = 20, nullable = false)
    private String userPw;

    @Column(name = "user_email", length = 50, nullable = false)
    private String userEmail;

    @Column(name = "user_join", nullable = false)
    private LocalDateTime userJoin;

    public UserEntity(String userId, String userPw, String userEmail, LocalDateTime userJoin) {
        this.userId = userId;
        this.userPw = userPw;
        this.userEmail = userEmail;
        this.userJoin = userJoin;
    }
}
