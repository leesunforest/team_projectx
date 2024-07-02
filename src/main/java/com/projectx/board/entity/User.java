package com.projectx.board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no", updatable = false)
    private Long userNo; // 회원 고유번호

    @Column(name = "user_id", nullable = false, length = 15)
    private String userId; // 회원 아이디

    @Column(name = "user_pw", nullable = false, length = 20)
    private String userPw; // 비밀번호

    @Column(name = "user_email", nullable = false, length = 50)
    private String userEmail; // 이메일

    @Column(name = "user_join", nullable = false)
    private LocalDateTime userJoin; // 가입일자

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites;

    @Builder
    public User(String userId, String userPw, String userEmail) {
        this.userId = userId;
        this.userPw = userPw;
        this.userEmail = userEmail;
        this.userJoin = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        this.userJoin = LocalDateTime.now();
    }
}
