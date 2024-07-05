package com.projectx.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    @Column(nullable = false, length = 50)
    private String userId;

    @Column(nullable = false, length = 100)
    private String userPw;

    @Column(nullable = false, length = 100)
    private String userEmail;

    @Column(nullable = false)
    private LocalDateTime userJoin;

    public UserEntity(String userId, String userPw, String userEmail, LocalDateTime userJoin) {
        this.userId = userId;
        this.userPw = userPw;
        this.userEmail = userEmail;
        this.userJoin = userJoin;
    }
}