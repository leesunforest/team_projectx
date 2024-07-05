package com.projectx.board.dto;

import com.projectx.board.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserSignupDTO {
    private String userId;
    private String userPw;
    private String userEmail;

    public UserEntity toUserEntity() {
        return new UserEntity(userId, userPw, userEmail, LocalDateTime.now());
    }
}
